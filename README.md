# FaceSlider

![YQWY5P](http://i.giphy.com/3oz8xSOP52hywzMXTi.gif)

Kullanım şekli:

Kütüphaneyi indirip projenize ekleyin.

Sonra kullanmak istediğiniz activity nin xml dosyasına aşağıdaki view ı ekleyin.

```
<view
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        class="com.KT.faceslider_library.FaceView"
        android:layout_centerVertical="true"
        android:layout_centerHorizontal="true"
        android:id="@+id/faceView" />
```
        
Ardından activity sınıfınızın içinde bu view ı tanımlayın.

```
FaceView faceView = (FaceView) findViewById(R.id.faceView);
        faceView.setOnTouchListener(faceView);
```

İşte hepsi bu kadar.
        
Seçilen yüz ifadesinin değerini getirmek isterseniz aşşağıdaki metodu çağırabilirsiniz.

```
faceView.getPuan();
```
