package com.xqd.myapplication.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import com.xqd.myapplication.util.DisplayUtil;

/**
 * Created by UJU105 on 2016/6/28.
 */
public class CanvasDemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new CustomView(this));
    }

    class CustomView extends View {
        Paint paint;
        Context mContext;

        public CustomView(Context context) {
            super(context);
            mContext = context;
            //设置一个笔刷大小是3的蓝色的画笔
            paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(DisplayUtil.dip2px(mContext, 3));
            paint.setStyle(Paint.Style.FILL);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
//            canvas.drawColor(Color.YELLOW);

            //画一条线
//            canvas.drawLine(DisplayUtil.dip2px(mContext, 10), DisplayUtil.dip2px(mContext, 10),
//                    DisplayUtil.dip2px(mContext, 200), DisplayUtil.dip2px(mContext, 200), paint);

            //画点
//            canvas.drawText("画点：", 10, 390, paint);
//            canvas.drawPoint(60, 390, paint);//画一个点
//            canvas.drawPoints(new float[]{60, 400, 65, 400, 70, 400}, paint);//画多个点

            //画矩形(3种方式)
//            canvas.drawRect(300, 300, 600, 600, paint);
//
//            Rect rect = new Rect(300, 300, 600, 600);
//            canvas.drawRect(rect, paint);

//            RectF rect = new RectF(250, 200, 400, 400);
//            canvas.drawRect(rect, paint);

            //圆角矩形
//            canvas.drawRoundRect(300, 300, 600, 600,
//                    30, //x轴的半径
//                    30, //y轴的半径
//                    paint);

            //画圆，中心x坐标，中心y坐标，半径，画笔
//            canvas.drawCircle(300, 300, 190, paint);

            //矩形区域内切椭圆
//            RectF oval = new RectF(300,300,700,500);
//            canvas.drawOval(oval, paint);

            //绘制弧线区域
//            Paint mPaint = new Paint();
//            mPaint.setColor(Color.YELLOW);//作为参照使用
//            RectF rect = new RectF(200, 300, 500, 600);
//            canvas.drawRect(rect, mPaint);
//            canvas.drawArc(rect, //弧线所使用的矩形区域大小
//                    90,  //开始角度(也是顺时针计算的)
//                    90, //扫过的角度（顺时针）
//                    true, //是否有弧的两边，True，有两边，False，只有一条弧
//                    paint);

            //画路径
//            Path path = new Path(); //定义一条路径
//            path.moveTo(100, 100); //移动到 坐标10,10
//            path.lineTo(100, 200);
//            path.lineTo(200, 80);
//            path.lineTo(100, 100);
//            canvas.drawPath(path, paint);
//            //画文字路径
//            canvas.drawTextOnPath("东哥是大帅比啊哈哈哈", path, 10, 10, paint);

            //画文字
            Paint paint = new Paint();
            paint.setColor(Color.RED);  //设置画笔颜色
            paint.setStrokeWidth(5);//设置画笔宽度
            paint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
            paint.setStyle(Paint.Style.FILL);//绘图样式，对于设文字和几何图形都有效
//            paint.setTextAlign(Paint.Align.LEFT);//设置文字对齐方式，取值：align.CENTER、align.LEFT或align.RIGHT
            paint.setTextSize(80);//设置文字大小
            //样式设置
            paint.setFakeBoldText(true);//设置是否为粗体文字
            paint.setUnderlineText(true);//设置下划线
            paint.setTextSkewX((float) -0.25);//设置字体水平倾斜度，普通斜体字是-0.25
            paint.setStrikeThruText(true);//设置带有删除线效果
            //其它设置
//            paint.setTextScaleX(2);//只会将水平方向拉伸，高度不会变
            canvas.drawText("东爷测试啦", 100, 300, paint);

            //按照既定点 绘制文本内容
//            canvas.drawPosText("东哥是大帅比啊哈哈哈", new float[]{
//                    100, 100, //第一个字母在坐标10,10
//                    200, 200, //第二个字母在坐标20,20
//                    300, 300, //....
//                    400, 400,
//                    500, 500,
//                    600, 600,
//                    700, 700,
//                    800, 800,
//                    900, 900,
//                    1000, 1000
//            }, paint);


//            canvas.drawText("画贝塞尔曲线:", 10, 310, paint);
//            paint.reset();
//            paint.setStyle(Paint.Style.STROKE);
//            paint.setTextSize(58);
//            Path path2=new Path();
//            path2.moveTo(200, 420);//设置Path的起点
//            path2.quadTo(150, 310, 470, 700); //设置贝塞尔曲线的控制点坐标和终点坐标
//            canvas.drawPath(path2, paint);//画出贝塞尔曲线

            //画图片，就是贴图
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_invite_sms);
//            canvas.drawBitmap(bitmap, 250,360, paint);


//****************************************画时钟（示例）*************************************************************************//

//            paint.setAntiAlias(true);//抗锯齿
//            paint.setDither(true);//防抖动
//            paint.setStyle(Paint.Style.STROKE); // 设置paint的风格为“空心”,当然也可以设置为"实心"(Paint.Style.FILL)
//            canvas.translate(canvas.getWidth() / 2, canvas.getHeight() / 2); //将位置移动画纸的中心
//            canvas.drawCircle(0, 0, 100, paint); //画圆圈 中心x坐标，中心y坐标，半径，画笔(画布不移动的话x，y坐标的0点都在左上方)
//
//            //使用path绘制路径文字
//            canvas.save();
//            canvas.translate(-75, -75);
//            Path path = new Path();
//            path.addArc(new RectF(0, 0, 150, 150), -180, 180);
//            Paint citePaint = new Paint(paint);
//            citePaint.setTextSize(14);
//            citePaint.setStrokeWidth(1);// 设置“空心”的外框的宽度
//            canvas.drawTextOnPath("http://www.android777.com", path, 28, 0, citePaint);
//            canvas.restore();
//
//            Paint tmpPaint = new Paint(paint); //小刻度画笔对象
//            tmpPaint.setStrokeWidth(1);
//
//            float y = 100;
//            int count = 60; //总刻度数
//
//            for (int i = 0; i < count; i++) {
//                if (i % 5 == 0) {
//                    canvas.drawLine(0f, y, 0, y + 12f, paint);
//                    canvas.drawText(String.valueOf(i / 5 + 1), -4f, y + 25f, tmpPaint);
//
//                } else {
//                    canvas.drawLine(0f, y, 0f, y + 5f, tmpPaint);
//                }
//                canvas.rotate(360 / count, 0f, 0f); //旋转画纸
//            }
//
//            //绘制指针
//            tmpPaint.setColor(Color.GRAY);
//            tmpPaint.setStrokeWidth(4);
//            canvas.drawCircle(0, 0, 7, tmpPaint);
//            tmpPaint.setStyle(Paint.Style.FILL);
//            tmpPaint.setColor(Color.YELLOW);
//            canvas.drawCircle(0, 0, 5, tmpPaint);
//            canvas.drawLine(0, 10, 0, -65, paint);


            // 创建画笔
//            Paint p = new Paint();
//            p.setColor(Color.RED);// 设置红色

//            canvas.drawText("画矩形：", 10, 80, p);
//            p.setColor(Color.GRAY);// 设置灰色
//            p.setStyle(Paint.Style.FILL);//设置填满
//            canvas.drawRect(60, 60, 80, 80, p);// 正方形
//            canvas.drawRect(60, 90, 160, 100, p);// 长方形
//
//            canvas.drawText("画扇形和椭圆:", 10, 120, p);
//        /* 设置渐变色 这个正方形的颜色是改变的 */
//            Shader mShader = new LinearGradient(0, 0, 100, 100,
//                    new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
//                            Color.LTGRAY}, null, Shader.TileMode.REPEAT); // 一个材质,打造出一个线性梯度沿著一条线。
//            p.setShader(mShader);
//            // p.setColor(Color.BLUE);
//            RectF oval2 = new RectF(60, 100, 200, 240);// 设置个新的长方形，扫描测量
//            canvas.drawArc(oval2, 200, 130, true, p);
//            // 画弧，第一个参数是RectF：该类是第二个参数是角度的开始，第三个参数是多少度，第四个参数是真的时候画扇形，是假的时候画弧线
//            //画椭圆，把oval改一下
//            oval2.set(210, 100, 250, 130);
//            canvas.drawOval(oval2, p);
//
//            canvas.drawText("画三角形：", 10, 200, p);
//            // 绘制这个三角形,你可以绘制任意多边形
//            Path path = new Path();
//            path.moveTo(80, 200);// 此点为多边形的起点
//            path.lineTo(120, 250);
//            path.lineTo(80, 250);
//            path.close(); // 使这些点构成封闭的多边形
//            canvas.drawPath(path, p);
//
//            // 你可以绘制很多任意多边形，比如下面画六连形
//            p.reset();//重置
//            p.setColor(Color.LTGRAY);
//            p.setStyle(Paint.Style.STROKE);//设置空心
//            Path path1 = new Path();
//            path1.moveTo(180, 200);
//            path1.lineTo(200, 200);
//            path1.lineTo(210, 210);
//            path1.lineTo(200, 220);
//            path1.lineTo(180, 220);
//            path1.lineTo(170, 210);
//            path1.close();//封闭
//            canvas.drawPath(path1, p);
            /*
             * Path类封装复合(多轮廓几何图形的路径
             * 由直线段*、二次曲线,和三次方曲线，也可画以油画。drawPath(路径、油漆),要么已填充的或抚摸
             * (基于油漆的风格),或者可以用于剪断或画画的文本在路径。
             */

            //画圆角矩形
//            p.setStyle(Paint.Style.FILL);//充满
//            p.setColor(Color.LTGRAY);
//            p.setAntiAlias(true);// 设置画笔的锯齿效果
//            canvas.drawText("画圆角矩形:", 10, 260, p);
//            RectF oval3 = new RectF(80, 260, 200, 300);// 设置个新的长方形
//            canvas.drawRoundRect(oval3, 20, 15, p);//第二个参数是x半径，第三个参数是y半径
//
//            //画贝塞尔曲线
//            canvas.drawText("画贝塞尔曲线:", 10, 310, p);
//            p.reset();
//            p.setStyle(Paint.Style.STROKE);
//            p.setColor(Color.GREEN);
//            Path path2=new Path();
//            path2.moveTo(100, 320);//设置Path的起点
//            path2.quadTo(150, 310, 170, 400); //设置贝塞尔曲线的控制点坐标和终点坐标
//            canvas.drawPath(path2, p);//画出贝塞尔曲线
//

//
//            //画图片，就是贴图
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_danmaku);
//            canvas.drawBitmap(bitmap, 250,360, p);
        }
    }
}
