package com.deuexmachina.selfiewithx;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private Button btnTool1;
    private RelativeLayout flMainWorkPlace;
    float dX, dY;
    FrameLayout flImgCont;
    ImageView imgContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        onActionPerform();
    }

    private void initialize() {

        btnTool1 = findViewById(R.id.btnTool1);
        flMainWorkPlace = findViewById(R.id.flMainWorkPlace);

    }

    private void onActionPerform() {
        btnTool1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                flImgCont = new FrameLayout(MainActivity.this);
                flImgCont.setTag("mainFrame");
                FrameLayout fButtonResize = generateResizeButton(flImgCont);
                 imgContent = getThatImage(flImgCont);
                int xWidth = imgContent.getLayoutParams().width;
                int xHeight = imgContent.getLayoutParams().height;

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(xWidth, xHeight);
                layoutParams.setMargins(((flMainWorkPlace.getWidth()/2)-layoutParams.width/2),
                        ((flMainWorkPlace.getHeight()/2)-layoutParams.height/2),0,0);
                flImgCont.setLayoutParams(layoutParams);
                flImgCont.setOnTouchListener(MainActivity.this);
                flMainWorkPlace.addView(flImgCont);


            }
        });
    }

    private ImageView getThatImage(FrameLayout flImgCont) {
        ImageView img = new ImageView(MainActivity.this);

        img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        img.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_shape));
        img.setImageResource(R.drawable.ic_launcher_foreground);
        flImgCont.addView(img);
        return img;
    }

    private FrameLayout generateResizeButton(FrameLayout f) {
        FrameLayout flResize = new FrameLayout(MainActivity.this);
        flResize.setTag("resize");
        flResize.setBackgroundColor(Color.GRAY);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(80,80,Gravity.BOTTOM|Gravity.RIGHT);
        //layoutParams.setMargins((rp.leftMargin)-layoutParams.width,(rp.topMargin)-layoutParams.height,0,0);
        flResize.setLayoutParams(layoutParams);
        f.addView(flResize);

        flResize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //flMainWorkPlace.removeView(view);
                //((FrameLayout)(view.getParent())).setVisibility(View.GONE);
                flMainWorkPlace.removeView((FrameLayout)(view.getParent()));
            }
        });

        return flResize;

    }

    @Override
        public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        dX = v.getX() - event.getRawX();
                        dY = v.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:

                        if(v.getTag().equals("mainFrame")) {
                            v.setY(event.getRawY() + dY);
                            v.setX(event.getRawX() + dX);

                        }else {
                            Toast.makeText(this, "HI", Toast.LENGTH_SHORT).show();

                        }
                        // .x(event.getRawX() + dX)
                        break;
                    case MotionEvent.ACTION_UP:

                        v.setY(v.getY());
                        v.setX(v.getX());
                        return true;

                    case MotionEvent.ACTION_POINTER_DOWN:
                        dX = v.getX() - event.getRawX();
                        dY = v.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        v.setY(v.getY());
                        v.setX(v.getX());
                        break;
                    default:
                        return false;
                }
                return true;
    }
}
