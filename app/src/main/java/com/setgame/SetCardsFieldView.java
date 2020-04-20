package com.setgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SetCardsFieldView extends View {

    private CardField cardField;
    private int width = 0, height = 0;
    private Paint p = new Paint();

    public void setCardField(CardField cardField) {
        this.cardField = cardField;
        cardField.initCoordinates(width, height);
        invalidate();
    }


    public SetCardsFieldView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public ArrayList<Card> checkSet() {
        boolean isSet = cardField.isSelectedAreSet();
        if (isSet) {
            return cardField.getSelectedCards();
        }
        Toast toast = Toast.makeText(getContext(),
                "This is not a set", Toast.LENGTH_LONG);
        toast.show();
        return null;
    }


    public void findSet() {
        if (!cardField.findSet()) {
            Toast toast = Toast.makeText(getContext(),
                    "There's no set", Toast.LENGTH_LONG);
            toast.show();
        }

        invalidate();

    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.width = getWidth();
        this.height = getHeight();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (cardField != null) {
            ArrayList<Card> cards = cardField.getCards();

            Integer cardBackground = Color.rgb(230, 230, 230);
            Integer selectedCardBackground = Color.rgb(191, 191, 191);
            Integer border = Color.rgb(108, 119, 139);


            for (Card c : cards) {

                if (cardField.getSelectedCards().contains(c)) {
                    p.setColor(selectedCardBackground);
                } else {
                    p.setColor(cardBackground);
                }

                //card
                p.setStyle(Paint.Style.FILL);
                int x2 = c.getX() + c.getWidth();
                int y2 = c.getY() + c.getHeight();

                canvas.drawRoundRect(new RectF(c.getX(), c.getY(), x2, y2), 5, 5, p);

                //border
                p.setColor(border);
                p.setStyle(Paint.Style.STROKE);
                p.setStrokeWidth(5);
                canvas.drawRoundRect(new RectF(c.getX(), c.getY(), x2, y2), 5, 5, p);

                //shapes inside card
                Integer color = getColor(c.getColor());
                p.setColor(color);


                int fig_height = (c.getHeight() - 80) / 3;
                int fig_x = c.getX() + 10;
                int fig_y = calculateFigY(c.getCount(), c.getHeight(), c.getY() , fig_height);

                int fig_x2 = fig_x + (c.getWidth()-20);
                int fig_y2 = fig_y + fig_height;

                p.setStrokeWidth(3);
                for (int i = 0; i < c.getCount(); i++) {

                    drawFillFigure(canvas, fig_x, fig_y, fig_x2 , fig_y2, c.getFill(), c.getShape());

                    fig_y += fig_height + 20;
                    fig_y2 = fig_y + fig_height;

                }
            }
        }

    }


    private void drawFillFigure(Canvas c, int x1, int y1, int x2, int y2, int fill, int shape) {
        switch (fill) {
            case 1: drawDashedLineFigure(c, x1, y1, x2 , y2, shape);
            break;
            case 2: {
                p.setStyle(Paint.Style.STROKE);
                drawShape(c, x1, y1, x2 , y2, shape);
            }
            break;
            case 3: {
                p.setStyle(Paint.Style.FILL);
                drawShape(c, x1, y1, x2 , y2, shape);
            }
            break;
        }
    }


    private void drawDashedLineFigure(Canvas c, int x1, int y1, int x2, int y2, int shape) {
        p.setStyle(Paint.Style.STROKE);
        double k = 1.2;
        int width = x2 - x1;
        int height = y2 - y1;

        int iterations = height / 6;

        int w2, h2, x_koef, y_koef;

        for (int i = 0; i < iterations; i++) {
            if (i == 0) {
                p.setStrokeWidth(3);
            } else {
                p.setStrokeWidth(1);
            }
            drawShape(c, x1, y1, x2 , y2, shape);

            w2 = (int) (width / k);
            h2 = (int) (height / k);

            x_koef = (width - w2)/2;
            y_koef = (height - h2)/2;
            x1 += x_koef;
            x2 -= x_koef;
            y1 += y_koef;
            y2 -= y_koef;

            height = h2;
            width = w2;

        }
    }


    private void drawShape(Canvas c, int x1, int y1, int x2, int y2, int shape) {
        switch (shape) {
            case 1: drawRhombus(c, x1, y1, x2, y2);
                break;
            case 2: c.drawRoundRect(new RectF(x1, y1, x2 , y2), 5, 5, p);
                break;
            case 3: c.drawOval( x1, y1, x2, y2, p);
                break;
        }

    }


    private void drawRhombus(Canvas c, int x1, int y1, int x2, int y2) {
        Path path = new Path();
        int center_width = (x2 + x1) / 2;
        int center_height = (y2 + y1) / 2;

        path.moveTo(center_width, y1); // Top
        path.lineTo(x1, center_height); // Left
        path.lineTo(center_width, y2); // Bottom
        path.lineTo(x2, center_height); // Right
        path.lineTo(center_width, y1); // Back to Top
        path.close();

        c.drawPath(path, p);
    }


    private int calculateFigY(int count, int card_height, int card_y, int fig_height) {
        int fig_y;
        int center = (card_y + (card_y + card_height)) / 2;
        switch (count) {
            case 1: fig_y = center - (fig_height / 2);
                break;
            case 2: fig_y = center - (fig_height + 10);
                break;
            default: fig_y = card_y + 20;
                break;
        }
        return  fig_y;
    }


    private Integer getColor(int card_color) {
        Integer red = Color.rgb(238, 0, 0);
        Integer violet = Color.rgb(95, 0, 156);
        Integer green = Color.rgb(255, 180, 1);

        if (card_color == 1) {
            return red;
        }

        if (card_color == 2) {
            return violet;
        }

        return green;

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (cardField.checkSelection(x, y)) {
                invalidate();
            }
        }

        return super.onTouchEvent(event);
    }
}
