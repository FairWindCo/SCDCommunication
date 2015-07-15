package ua.pp.fairwind.javafx.effects;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * Created by Сергей on 18.08.2014.
 */
public class TestEffectsWindow extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        //test3(primaryStage);
        test5(primaryStage);
        //testGradient(primaryStage);
        primaryStage.show();
    }


    public static Node buttonUP(int width,int height,Color buttonColor,boolean isUP,boolean isBloom){
        Group osnovaGroup=new Group();
        double radius=width<height?width*0.4:height*0.4;
        int fdistance=(int)(3*radius/40);
        Rectangle base=new Rectangle(0,0,width,height);

        Circle fonokontovka=new Circle(width/2+fdistance,height/2+fdistance,radius);
        Circle okontovka=new Circle(width/2,height/2,radius);

        Color lightButtonColor=buttonColor.brighter();
        Color darkButtonColor=buttonColor.darker();

        RadialGradient gradient_metal_okontovka=new RadialGradient(0.0,0.0,width/2,height/2,radius,false,CycleMethod.NO_CYCLE,new Stop(1,Color.DARKGRAY),new Stop(0.9,Color.LIGHTGRAY),new Stop(0.85,Color.WHITE),new Stop(0.81,Color.DARKGRAY),new Stop(0.8,Color.BLACK),new Stop(0,Color.BLACK));
        LinearGradient gradient_metal_fonokontovka=new LinearGradient(0,0,0,height*2,false, CycleMethod.NO_CYCLE,new Stop(0,Color.WHITE),new Stop(1,Color.BLACK));
        LinearGradient light_source=new LinearGradient(0,0,width,height,false, CycleMethod.NO_CYCLE,new Stop(0,Color.WHITE),new Stop(1,Color.BLACK));
        okontovka.setFill(gradient_metal_okontovka);
        okontovka.setStroke(Color.rgb(70, 70, 70));
        fonokontovka.setFill(gradient_metal_fonokontovka);
        fonokontovka.setStroke(Color.BLACK);

        osnovaGroup.getChildren().add(fonokontovka);
        osnovaGroup.getChildren().add(okontovka);


        Shape res=Shape.union(okontovka,fonokontovka);
        Group lightGroup=new Group();
        osnovaGroup.setClip(res);
        Blend blendEffect=new Blend(BlendMode.SOFT_LIGHT);
        ColorInput input=new ColorInput();
        input.setPaint(light_source);
        input.setX(0);
        input.setY(0);
        input.setHeight(height);
        input.setWidth(width);
        blendEffect.setTopInput(input);
        osnovaGroup.setEffect(blendEffect);


        Group button=new Group();
        int button_radius=(int)(radius*0.8);
        if(isUP) {
            Group buttonConstract=new Group();
            int distance=(int)(3*radius/40);
            RadialGradient button_top_color;
            if(isBloom) {
                button_top_color = new RadialGradient(0.0, 0.0, width / 2 - distance, height / 2 - distance, button_radius, false, CycleMethod.NO_CYCLE, new Stop(1, buttonColor), new Stop(0.6, lightButtonColor),new Stop(0, lightButtonColor));
            } else {
                button_top_color = new RadialGradient(0.0, 0.0, width / 2 - distance, height / 2 - distance, button_radius, false, CycleMethod.NO_CYCLE, new Stop(1, darkButtonColor), new Stop(0.9, buttonColor));
            }
            LinearGradient button_bottom_color = new LinearGradient(0, 0, width, height, false, CycleMethod.NO_CYCLE, new Stop(0, darkButtonColor), new Stop(0.6, darkButtonColor), new Stop(1, Color.BLACK));
            Circle knopka_top = new Circle(width / 2 - distance, height / 2 - distance, button_radius);
            knopka_top.setFill(button_top_color);
            Circle knopka_buttom = new Circle(width / 2, height / 2, button_radius);

            Blend blendEffect2;
            ColorInput input2 = new ColorInput();
            Paint light_source2;
            if(isBloom) {
                blendEffect2 = new Blend(BlendMode.LIGHTEN);
                light_source2 = new RadialGradient(0.0, 0.0, width / 2 - distance, height / 2 - distance, button_radius*0.8, false, CycleMethod.NO_CYCLE, new Stop(0, Color.WHITE),new Stop(0.3, lightButtonColor), new Stop(1, darkButtonColor));
            } else {
                blendEffect2 = new Blend(BlendMode.HARD_LIGHT);
                light_source2 = new LinearGradient(0, 0, height, width, false, CycleMethod.NO_CYCLE, new Stop(0, buttonColor), new Stop(1, Color.BLACK));
            }
            input2.setPaint(light_source2);
            input2.setX(0);
            input2.setY(0);
            input2.setHeight(height);
            input2.setWidth(width);
            blendEffect2.setTopInput(input2);



            knopka_buttom.setFill(button_bottom_color);

            buttonConstract.getChildren().add(knopka_buttom);
            buttonConstract.getChildren().add(knopka_top);
            Shape resButton = Shape.union(knopka_buttom, knopka_top);
            buttonConstract.setClip(resButton);
            buttonConstract.setEffect(blendEffect2);
            button.getChildren().add(buttonConstract);
            if(isBloom) {
                GaussianBlur blur = new GaussianBlur(distance*0.7);
                DropShadow glow = new DropShadow();
                glow.setColor(lightButtonColor);
                glow.setOffsetX(0);
                glow.setOffsetY(0);
                glow.setRadius(distance * 5);
                blur.setInput(glow);

                button.setEffect(blur);
            }

        } else {
            int distance=(int)(2*radius/40);
            LinearGradient button_bottom_color = new LinearGradient(0, 0, 0, height, false, CycleMethod.NO_CYCLE, new Stop(0, Color.DARKGRAY), new Stop(0.4, Color.BLACK));
            RadialGradient button_top_color = new RadialGradient(0.0, 0.0, width / 2 + distance, height / 2 + distance, button_radius, false, CycleMethod.NO_CYCLE, new Stop(1, darkButtonColor), new Stop(0.9, buttonColor));
            Circle knopka_top = new Circle(width / 2 + distance, height / 2 + distance, button_radius);
            Circle knopka_buttom = new Circle(width / 2, height / 2, button_radius);
            knopka_buttom.setFill(button_bottom_color);
            knopka_top.setFill(button_top_color);
            Blend blendEffect2 = new Blend(BlendMode.DARKEN);
            ColorInput input2 = new ColorInput();
            LinearGradient light_source2 = new LinearGradient(0, 0, height, width, false, CycleMethod.NO_CYCLE, new Stop(1, Color.LIGHTGRAY), new Stop(0, darkButtonColor));
            input2.setPaint(light_source2);
            input2.setX(0);
            input2.setY(0);
            input2.setHeight(height);
            input2.setWidth(width);
            blendEffect2.setTopInput(input2);
            Shape resButton = Shape.union(Shape.intersect(knopka_buttom, knopka_top),knopka_buttom);
            button.getChildren().add(knopka_buttom);
            button.getChildren().add(knopka_top);
            button.setClip(resButton);
            button.setEffect(blendEffect2);

        }

        /*
        Circle sub=new Circle(width/2,height/2-distance,button_radius);
        LinearGradient sub_color=new LinearGradient(0,0,0,radius*2,false, CycleMethod.NO_CYCLE,new Stop(0,Color.TRANSPARENT),new Stop(1,Color.rgb(255,255,255,0.99)));
        sub.setFill(sub_color);
        button.getChildren().add(sub);/**/

        lightGroup.getChildren().add(osnovaGroup);
        lightGroup.getChildren().add(button);

        lightGroup.setClip(base);
        return lightGroup;
    }

    public static void test4(Stage primaryStage) {
        Group back = new Group();
        Scene scene=new Scene(back,800,600, Color.WHITE);
        primaryStage.setScene(scene);
    }

    public static void test5(Stage primaryStage) {
        Group back = new Group();
        Scene scene=new Scene(back,800,600, Color.BLACK);

        LinearGradient rainBow=new LinearGradient(0,300,800,300,false,CycleMethod.NO_CYCLE,new Stop(0,Color.RED),new Stop(0.16,Color.ORANGE),new Stop(0.33,Color.YELLOW),new Stop(0.49,Color.GREEN),new Stop(0.65,Color.BLUE),new Stop(0.82,Color.BLUEVIOLET));
        Line osnova=new Line(10,300,780,300);
        Line line=new Line(10,300,780,300);
        osnova.setStroke(Color.LIGHTGRAY);
        line.setStroke(rainBow);
        line.setStrokeWidth(2);
        osnova.setStrokeWidth(5);

        GaussianBlur blur = new GaussianBlur(5);
        DropShadow glow = new DropShadow();
        glow.setColor(Color.GRAY);
        glow.setOffsetX(0);
        glow.setOffsetY(0);
        glow.setRadius(10);
        blur.setInput(glow);
        osnova.setEffect(blur);
        Group osnovagrp=new Group();


        Rectangle rect=new Rectangle(0,0,800,600);
        rect.setFill(rainBow);
        rect.setBlendMode(BlendMode.OVERLAY);

        Group splach=new Group();
        Circle circl=new Circle(300,300,6);
        Circle circl2=new Circle(300,300,3);
        circl.setEffect(blur);
        circl.setFill(Color.LIGHTGRAY);
        circl2.setFill(Color.GRAY);
        splach.getChildren().addAll(circl,circl2);

        osnovagrp.getChildren().add(osnova);
        osnovagrp.getChildren().add(splach);
        osnovagrp.getChildren().add(rect);



        back.getChildren().add(osnovagrp);
        back.getChildren().add(line);

        PathTransition transition = new PathTransition();
        transition.setPath(line);
        transition.setNode(splach);

        transition.setDuration(Duration.seconds(getRandom()));
        transition.setCycleCount(-1);
        transition.play();


        primaryStage.setScene(scene);


    }

    public static void testGradient(Stage primaryStage) {
        Group back = new Group();
        Scene scene=new Scene(back,800,600, Color.WHITE);
        Rectangle rect=new Rectangle(0,0,800,600);

        Paint light_source2 = new RadialGradient(0.0, 0.0, 150 / 2 - 2, 150 / 2 - 2, 140*1.2, false, CycleMethod.NO_CYCLE, new Stop(0, Color.WHITE),new Stop(0.05, Color.WHITE), new Stop(1, Color.DARKGRAY));
        rect.setFill(light_source2);

        back.getChildren().add(rect);

        primaryStage.setScene(scene);
    }

    public static void test3(Stage primaryStage) {
        Group back = new Group();
        Scene scene=new Scene(back,800,600, Color.WHITE);
        int width=120;
        int height=120;
        WritableImage img1=new WritableImage(width,height);
        WritableImage img2=new WritableImage(width,height);
        WritableImage img3=new WritableImage(width,height);
        WritableImage img4=new WritableImage(width,height);
        SnapshotParameters pramr=new SnapshotParameters();
        pramr.setFill(Color.TRANSPARENT);
        buttonUP(width,height,Color.RED,false,true).snapshot(pramr,img1);
        buttonUP(width,height,Color.BLUE,true,true).snapshot(pramr,img2);
        buttonUP(width,height,Color.BLUE,true,false).snapshot(pramr,img3);
        buttonUP(width,height,Color.RED,true,false).snapshot(pramr,img4);
        ImageView imm1=new ImageView(img1);
        ImageView imm2=new ImageView(img2);
        ImageView imm3=new ImageView(img3);
        ImageView imm4=new ImageView(img4);
        imm1.setX(10);
        imm2.setX(150);
        imm3.setX(300);
        imm4.setX(450);
        back.getChildren().add(imm1);
        back.getChildren().add(imm2);
        back.getChildren().add(imm3);
        back.getChildren().add(imm4);
        primaryStage.setScene(scene);
    }

    public static void test1(Stage primaryStage){
        Group back=new Group();
        Scene scene=new Scene(back,800,600, Color.BLACK);
        Line line=new Line(10,300,790,300);
        WritableImage img=new WritableImage(50,50);

        ImagePattern pattrent=new ImagePattern(img,0,0,1,50,false);
        Group sungrp=new Group();
        Line lineg=new Line(0,0,0,50);
        lineg.setStrokeWidth(50);
        lineg.setStroke(new LinearGradient(0,0,0,25,false, CycleMethod.REFLECT,new Stop(1,Color.TRANSPARENT),new Stop(0,Color.RED)));
        Circle point=new Circle(5,Color.BLUE);
        point.setCenterX(0);
        point.setCenterY(0);
        sungrp.getChildren().add(lineg);
        //sungrp.getChildren().add(point);
        //lineg.setStroke(Color.RED);
        SnapshotParameters pramr=new SnapshotParameters();
        pramr.setFill(Color.TRANSPARENT);
        sungrp.snapshot(pramr,img);
        //line.setFill(Color.RED);
        //line.setEffect(new Glow(0.5));
        line.setStrokeWidth(100);
        line.setStroke(pattrent);
        back.getChildren().add(line);
        back.getChildren().add(new ImageView(img));
        Path path=new Path();
        path.setStrokeWidth(5);
        path.setStroke(pattrent);
        path.getElements().add(new MoveTo(10,10));
        path.getElements().add(new LineTo(100,50));
        path.getElements().add(new LineTo(185,10));
        path.getElements().add(new LineTo(234,150));
        back.getChildren().add(path);
        primaryStage.setScene(scene);
    }

    public static void test2(Stage primaryStage){
        DropShadow glow=new DropShadow();
        glow.setColor(Color.RED);
        glow.setOffsetX(0);
        glow.setOffsetY(0);
        glow.setRadius(5);
        GaussianBlur mblur=new GaussianBlur();
        mblur.setInput(glow);
        SimpleIntegerProperty radius=new SimpleIntegerProperty(3);
        glow.radiusProperty().bindBidirectional(radius);
        mblur.radiusProperty().bindBidirectional(radius);

        Group sceneGRP=new Group();
        Group back=new Group();
        sceneGRP.getChildren().add(back);
        Scene scene=new Scene(sceneGRP,800,600, Color.BLACK);
        Line line=new Line(10,300,790,300);
        Line line2=new Line(10,300,790,300);
        //line.setFill(Color.RED);
        //line.setEffect(new Glow(0.5));
        line.setStrokeWidth(8);
        line.setStroke(Color.RED);
        line2.setStrokeWidth(3);
        line2.setStroke(Color.RED);
        line.setEffect(new GaussianBlur(20));

        back.getChildren().add(line);
        back.getChildren().add(line2);
        Path path=new Path();
        path.setEffect(glow);
        path.setStrokeWidth(5);
        path.setStroke(Color.RED);
        path.getElements().add(new MoveTo(10, 10));
        path.getElements().add(new LineTo(100,50));
        path.getElements().add(new LineTo(185,10));
        path.getElements().add(new LineTo(234,150));
        back.getChildren().add(path);
        back.setEffect(new Glow(0.8));
        Explode exp=new Explode();
        exp.explode();
        sceneGRP.getChildren().add(exp);


        Timeline tml=new Timeline();
        tml.setCycleCount(-1);
        tml.getKeyFrames().add(new KeyFrame(Duration.millis(0),new KeyValue(radius,10)));
        tml.getKeyFrames().add(new KeyFrame(Duration.millis(400),new KeyValue(radius,20)));
        tml.getKeyFrames().add(new KeyFrame(Duration.millis(800),new KeyValue(radius,10)));
        tml.play();

        back.getChildren().add(createSplash(line));
        back.getChildren().add(createSplash(line));
        back.getChildren().add(createSplash(line));
        back.getChildren().add(createSplash(line));
        back.getChildren().add(createSplash(line));

        back.getChildren().add(createSplash(path));
        back.getChildren().add(createSplash(path));
        primaryStage.setScene(scene);

    }

    public static Node createSplash(Shape line){
        DropShadow glow=new DropShadow();
        glow.setColor(Color.RED);
        glow.setOffsetX(0);
        glow.setOffsetY(0);
        glow.setRadius(5);
        GaussianBlur mblur=new GaussianBlur();
        mblur.setInput(glow);
        Circle particle=new Circle(0,0,3,Color.RED);
        SimpleIntegerProperty radius=new SimpleIntegerProperty(3);
        glow.radiusProperty().bindBidirectional(radius);
        mblur.radiusProperty().bindBidirectional(radius);
        SimpleIntegerProperty circele=new SimpleIntegerProperty(3);
        particle.radiusProperty().bindBidirectional(circele);
        particle.setEffect(mblur);

        PathTransition transition = new PathTransition();
        transition.setPath(line);
        transition.setNode(particle);

        transition.setDuration(Duration.seconds(getRandom()));
        transition.setCycleCount(-1);
        transition.play();

        Timeline tml=new Timeline();
        tml.setCycleCount(-1);
        tml.getKeyFrames().add(new KeyFrame(Duration.millis(0),new KeyValue(circele,2)));
        tml.getKeyFrames().add(new KeyFrame(Duration.millis(400),new KeyValue(circele,10)));
        tml.getKeyFrames().add(new KeyFrame(Duration.millis(800),new KeyValue(circele,2)));
        tml.getKeyFrames().add(new KeyFrame(Duration.millis(0),new KeyValue(radius,10)));
        tml.getKeyFrames().add(new KeyFrame(Duration.millis(400),new KeyValue(radius,20)));
        tml.getKeyFrames().add(new KeyFrame(Duration.millis(800),new KeyValue(radius,10)));
        tml.play();

        return particle;
    }

    static public Double getRandom(){
        double rand = Math.random()*15;
        return rand>5?rand:getRandom();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
