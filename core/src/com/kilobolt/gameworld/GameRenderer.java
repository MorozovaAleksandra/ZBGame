package com.kilobolt.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kilobolt.gameobjects.Bird;
import com.kilobolt.gameobjects.Grass;
import com.kilobolt.gameobjects.Pipe;
import com.kilobolt.gameobjects.ScrollHandler;
import com.kilobolt.zbHelpers.AssetLoader;


public class GameRenderer {

    private GameWorld myWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batcher;
    private int gameHeight;
    private int midPointY;

    private Bird bird;
    private ScrollHandler scroller;
    private Grass frontGrass;
    private Grass backGrass;
    private Pipe firstPipe;
    private Pipe secondPipe;
    private Pipe thirdPipe;

    private TextureRegion bg, grass;
    private Animation birdAnimation;
    private TextureRegion birdMid, birdDown, birdUp;
    private TextureRegion skullUp, skullDown, bar;

    public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
        myWorld = world;
        this.gameHeight = gameHeight;
        this.midPointY = midPointY;

        cam = new OrthographicCamera();
        cam.setToOrtho(true, 136, 204);

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        initGameObjects();
        initAssets();
    }

    private void initGameObjects() {
        bird = myWorld.getBird();
        scroller = myWorld.getScroller();
        frontGrass = scroller.getFrontGrass();
        backGrass = scroller.getBackGrass();
        firstPipe = scroller.getFirstPipe();
        secondPipe = scroller.getSecondPipe();
        thirdPipe = scroller.getThirdPipe();
    }

    private void initAssets() {
        bg = AssetLoader.bg;
        grass = AssetLoader.grass;
        birdAnimation = AssetLoader.birdAnimation;
        birdMid = AssetLoader.bird;
        birdDown = AssetLoader.birdDown;
        birdUp = AssetLoader.birdUp;
        skullUp = AssetLoader.skullUp;
        skullDown = AssetLoader.skullDown;
        bar = AssetLoader.bar;
    }

    public void drawGrass() {
        //рисуем траву
        batcher.draw(grass, frontGrass.getX(), frontGrass.getY(), frontGrass.getWidth(), frontGrass.getHeight());
        batcher.draw(grass, backGrass.getX(), backGrass.getY(), backGrass.getWidth(), backGrass.getHeight());
    }

    private void drawSkulls() {
        // Временный код, извините за кашу :)
        // Мы это починим, как только закончим с Pipe классом.
        batcher.draw(skullUp, firstPipe.getX() - 1,
                firstPipe.getY() + firstPipe.getHeight() - 14, 24, 14);
        batcher.draw(skullDown, firstPipe.getX() - 1,
                firstPipe.getY() + firstPipe.getHeight() + 45, 24, 14);

        batcher.draw(skullUp, secondPipe.getX() - 1,
                secondPipe.getY() + secondPipe.getHeight() - 14, 24, 14);
        batcher.draw(skullDown, secondPipe.getX() - 1,
                secondPipe.getY() + secondPipe.getHeight() + 45, 24, 14);

        batcher.draw(skullUp, thirdPipe.getX() - 1,
                thirdPipe.getY() + thirdPipe.getHeight() - 14, 24, 14);
        batcher.draw(skullDown, thirdPipe.getX() - 1,
                thirdPipe.getY() + thirdPipe.getHeight() + 45, 24, 14);
    }

    private void drawPipes() {
        // Временный код, извините за кашу :)
        // Мы это починим, как только закончим с Pipe классом.
        batcher.draw(bar, firstPipe.getX(), firstPipe.getY(), firstPipe.getWidth(),
                firstPipe.getHeight());
        batcher.draw(bar, firstPipe.getX(), firstPipe.getY() + firstPipe.getHeight() + 45,
                firstPipe.getWidth(), midPointY + 66 - (firstPipe.getHeight() + 45));

        batcher.draw(bar, secondPipe.getX(), secondPipe.getY(), secondPipe.getWidth(),
                secondPipe.getHeight());
        batcher.draw(bar, secondPipe.getX(), secondPipe.getY() + secondPipe.getHeight() + 45,
                secondPipe.getWidth(), midPointY + 66 - (secondPipe.getHeight() + 45));

        batcher.draw(bar, thirdPipe.getX(), thirdPipe.getY(), thirdPipe.getWidth(),
                thirdPipe.getHeight());
        batcher.draw(bar, thirdPipe.getX(), thirdPipe.getY() + thirdPipe.getHeight() + 45,
                thirdPipe.getWidth(), midPointY + 66 - (thirdPipe.getHeight() + 45));
    }

    public void render(float runTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 136, midPointY + 66);


        shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 66, 136, 11);


        shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 77, 136, 52);

        shapeRenderer.end();

        batcher.begin();
        batcher.disableBlending();
        batcher.draw(AssetLoader.bg, 0, midPointY + 23, 136, 43);
        batcher.enableBlending();
        //TODO
        // 1. Отрисовываем Grass
        drawGrass();

        // 2. Отрисовываем Pipes
        drawPipes();
        batcher.enableBlending();

        // 3. Отрисовываем Skulls (необходима прозрачность)
        drawSkulls();
        if (bird.shouldntFlap()) {
            batcher.draw(birdMid, bird.getX(), bird.getY(),
                    bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
                    bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());

        } else {
            batcher.draw((TextureRegion) birdAnimation.getKeyFrame(runTime), bird.getX(),
                    bird.getY(), bird.getWidth() / 2.0f,
                    bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(),
                    1, 1, bird.getRotation());
        }

        batcher.end();
    }
}

