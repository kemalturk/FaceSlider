/*
    FaceSlider kütüphanesi

    Burada yazılan tüm kodlar içerisinde bulunduğu MQR Projesi
    için özel olarak tasarlanmıştır ve MQR Projesinin Android işletim
    sistemi kullanan cihazlarında MQR uygulamasının çalışmasını
    sağlayacak şekilde tasarlanıp kodlanmıştır.

    Kütüphanenin genel amacı kullanıcının animasyonlu bir yüz ifadesinin aşşağı yukarı kaydırarak geri bildirim
    için seçim yapmasını sağlamaktadır.FaceView sınıfının getPuan metodu kullanıcının seçtiği yüz ifadesine
    göre 1 ile 5 arası bir değer döndürmektedir.

    Copyright (C) 2016 Kemal Türk

    Bu kütüphane özgür yazılımdır:
    Özgür Yazılım Vakfı tarafından yayımlanan GNU Kütüphane Genel Kamu Lisansı’nın sürüm 3 ya da (
    isteğinize bağlı olarak) daha sonraki sürümlerinin hükümleri altında yeniden dağıtabilir ve/veya değiştirebilirsiniz.

    Bu kütüphane, yararlı olması umuduyla yazılmış olup, programın BİR TEMİNATI YOKTUR;
    TİCARETİNİN YAPILABİLİRLİĞİNE VE ÖZEL BİR AMAÇ İÇİN UYGUNLUĞUNA dair bir teminat da vermez.
    Ayrıntılar için GNU Kütüphane Genel Kamu Lisansı’na göz atınız.

    Bu kütüphaneyle birlikte GNU Kütüphane Genel Kamu Lisansı’nın bir kopyasını elde etmiş olmanız gerekir;
    eğer elinize ulaşmadıysa

    Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor,
    Boston, MA 02110-1301, ABD adresine yazın.

 */

package com.KT.faceslider_library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class FaceView extends View implements OnTouchListener {

	private Paint paint;
	private int curveRadius;

	private int first_y;
	private int second_y;
	private int sifirla;
	private String puan;

	static final String logTag = "ActivitySwipeDetector";
	private float downX, downY, upX, upY;
	static final int MIN_DISTANCE = 1;
	private MotionEvent event;

	public String getPuan() {
		return puan;
	}

	public FaceView(Context context) {
		super(context);
	}

	public FaceView(Context context, AttributeSet attributeSet){
		super(context,attributeSet);
		
		paint = new Paint();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		puanhesapla(curveRadius);

		renkayarla();

		paint.setStrokeWidth(12);
		paint.setStyle(Paint.Style.STROKE);

		canvas.drawCircle(canvas.getWidth()/2,canvas.getHeight()/2,230,paint);

		int startx_sol = canvas.getWidth()/2-120;
		int stopx_sol = canvas.getWidth()/2-65;
		int starty_sol = canvas.getHeight()/2-50;
		int stopy_sol = canvas.getHeight()/2-50;

		int startx_sag = canvas.getWidth()/2+120;
		int stopx_sag = canvas.getWidth()/2+65;
		int starty_sag = canvas.getHeight()/2-50;
		int stopy_sag = canvas.getHeight()/2-50;

		int startx_agiz = canvas.getWidth()/2-55;
		int stopx_agiz = canvas.getWidth()/2+55;
		int starty_agiz = canvas.getHeight()/2+60;
		int stopy_agiz = canvas.getHeight()/2+60;


		if (curveRadius > 0 && curveRadius < 75 ){

			drawCurvedArrow(startx_sol,starty_sol,stopx_sol,stopy_sol,0,canvas,paint); //sol göz

			drawCurvedArrow(startx_sag,starty_sag,stopx_sag,stopy_sag,0,canvas,paint); //sağ göz

			drawCurvedArrow(startx_agiz,starty_agiz,stopx_agiz,stopy_agiz,curveRadius,canvas,paint); //ağız

		}else if(curveRadius < 0 && curveRadius > -75) {

			drawCurvedArrow(startx_sol,starty_sol,stopx_sol,stopy_sol,-curveRadius,canvas,paint); //sol göz

			drawCurvedArrow(startx_sag,starty_sag,stopx_sag,stopy_sag,curveRadius,canvas,paint); //sağ göz

			drawCurvedArrow(startx_agiz,starty_agiz,stopx_agiz,stopy_agiz,curveRadius,canvas,paint); //ağız
		}else if (curveRadius >= 75){

			drawCurvedArrow(startx_sol,starty_sol,stopx_sol,stopy_sol,0,canvas,paint); //sol göz

			drawCurvedArrow(startx_sag,starty_sag,stopx_sag,stopy_sag,0,canvas,paint); //sağ göz

			drawCurvedArrow(startx_agiz,starty_agiz,stopx_agiz,stopy_agiz,75,canvas,paint); //ağız
		}else if (curveRadius <= -75){

			drawCurvedArrow(startx_sol,starty_sol,stopx_sol,stopy_sol,75,canvas,paint); //sol göz

			drawCurvedArrow(startx_sag,starty_sag,stopx_sag,stopy_sag,-75,canvas,paint); //sağ göz

			drawCurvedArrow(startx_agiz,starty_agiz,stopx_agiz,stopy_agiz,-75,canvas,paint); //ağız
		}else {

			drawCurvedArrow(startx_sol,starty_sol,stopx_sol,stopy_sol,0,canvas,paint); //sol göz

			drawCurvedArrow(startx_sag,starty_sag,stopx_sag,stopy_sag,0,canvas,paint); //sağ göz

			drawCurvedArrow(startx_agiz,starty_agiz,stopx_agiz,stopy_agiz,0,canvas,paint); //ağız

		}

		invalidate();
		
	}

	private void puanhesapla(int curveRadius){


		if (curveRadius <= -50){

			puan = "5";

		}else if (curveRadius < -25 && curveRadius > -50){

			puan = "4";

		}else if (curveRadius <= 25 && curveRadius >= -25){

			puan = "3";
		}else if (curveRadius > 25 && curveRadius < 50){

			puan = "2";
		}else if (curveRadius >= 50){

			puan = "1";

		}
	}

	private void renkayarla(){

		if (puan.equals("1")){
			paint.setColor(Color.parseColor("#dd2f34"));
		}else if (puan.equals("2")){
			paint.setColor(Color.parseColor("#e18332"));
		}else if (puan.equals("3")){
			paint.setColor(Color.parseColor("#892dda"));
		}else if (puan.equals("4")){
			paint.setColor(Color.parseColor("#2d7bd8"));
		}else if (puan.equals("5")){
			paint.setColor(Color.parseColor("#c1e334"));
		}

	}

	public void drawCurvedArrow(int x1, int y1, int x2, int y2, int curveRadius,Canvas canvas,Paint paint) {

		final Path path = new Path();
		int midX            = (x1 + x2)/2;
		int midY            = (y1 + y2)/2;
		float xDiff         = midX - x1;
		float yDiff         = midY - y1;
		double angle        = (Math.atan2(yDiff, xDiff) * (180 / Math.PI)) - 90;
		double angleRadians = Math.toRadians(angle);
		float pointX        = (float) (midX + curveRadius * Math.cos(angleRadians));
		float pointY        = (float) (midY + curveRadius * Math.sin(angleRadians));

		path.moveTo(x1, y1);
		path.cubicTo(x1,y1,pointX, pointY, x2, y2);
		canvas.drawPath(path, paint);

	}

	public void onDownSwipe(){

		int kayan_y = (int) event.getY();
		sifirla = first_y-kayan_y;

		curveRadius = (sifirla+curveRadius)/4;

	}

	public void onUpSwipe(){

		int kayan_y = (int) event.getY();
		sifirla = first_y-kayan_y;

		curveRadius = (sifirla+curveRadius)/4;

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		this.event = event;

		switch(event.getAction()){

			case MotionEvent.ACTION_DOWN:

				first_y = (int) event.getY();

				downX = event.getX();
				downY = event.getY();

				break;

			case MotionEvent.ACTION_UP:

				second_y = curveRadius;

				break;

			case MotionEvent.ACTION_MOVE:

				upX = event.getX();
				upY = event.getY();

				float deltaX = downX - upX;
				float deltaY = downY - upY;

				// swipe horizontal?
				if(Math.abs(deltaX) > Math.abs(deltaY))
				{
					if(Math.abs(deltaX) > MIN_DISTANCE){
						// left or right
					}
					else {
						Log.i(logTag, "Horizontal Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE);
						return false; // We don't consume the event
					}
				}
				// swipe vertical?
				else
				{
					if(Math.abs(deltaY) > MIN_DISTANCE){
						// top or down
						if(deltaY < 0) { this.onDownSwipe(); return true; }
						if(deltaY > 0) { this.onUpSwipe(); return true; }
					}
					else {
						Log.i(logTag, "Vertical Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE);
						return false; // We don't consume the event
					}
				}

				break;
		}
		return true;
	}
}