package com.setgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SetCardsFieldView extends View {

    private CardField cardField;
    private int width = 0, height = 0;
    Paint p = new Paint();

    public void setCardField(CardField cardField) {
        this.cardField = cardField;
        cardField.initCoordinates(width, height);
        invalidate();
    }

    public SetCardsFieldView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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
            ArrayList<Card> cards = cardField.cards;

            Integer cardBackground = Color.rgb(255, 230, 255);
            Integer text = Color.rgb(0, 0, 0);



            for (Card c : cards) {
                p.setColor(cardBackground);
                p.setStyle(Paint.Style.FILL);
                int x2 = c.x + c.width;
                int y2 = c.y + c.height;

                canvas.drawRect(c.x, c.y, x2, y2, p);
                String s = c.color + "\n" + c.count + "\n" + c.fill + "\n" + c.shape;
                p.setColor(text);

                Integer color = getColor(c.color);
                p.setColor(color);


                int fig_height = (c.height - 80) / 3;
                int fig_x = c.x + 10;
                int fig_y = calculateFigY(c.count, c.height, c.y , fig_height);

                int fig_x2 = fig_x + (c.width-20);
                int fig_y2 = fig_y + fig_height;

                p.setStrokeWidth(2);
                for (int i = 0; i < c.count; i++) {

                    drawFillFigure(canvas, fig_x, fig_y, fig_x2 , fig_y2, c.fill, c.shape);

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
                c.drawOval( x1, y1, x2, y2, p);
            }
            break;
        }
    }

    private void drawDashedLineFigure(Canvas c, int x1, int y1, int x2, int y2, int shape) {
        p.setStyle(Paint.Style.STROKE);
        double k = 1.2;
        int width = x2 - x1;
        int height = y2 - y1;

        int w2, h2, x_koef, y_koef;

        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                p.setStrokeWidth(2);
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
        Integer red = Color.rgb(255, 102, 102);
        Integer violet = Color.rgb(51, 0, 102);
        Integer green = Color.rgb(0, 153, 51);

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
        // TODO: в случе касания, определить, какой карты коснулись

        return super.onTouchEvent(event);
    }
}
