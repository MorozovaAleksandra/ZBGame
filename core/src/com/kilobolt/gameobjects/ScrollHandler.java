package com.kilobolt.gameobjects;

public class ScrollHandler {
    private Grass frontGrass, backGrass;
    private Pipe firstPipe, secondPipe, thirdPipe;

    public static final int SCROLL_SPEED = -59;
    public static final int PIPE_GAP = 49;

    public ScrollHandler(float yPos) {
        frontGrass = new Grass(0, yPos, 143, 11, SCROLL_SPEED);
        backGrass = new Grass(frontGrass.getTailX(), 210, 0, 22, SCROLL_SPEED);

        firstPipe = new Pipe(210, 0, 22, 60, SCROLL_SPEED, yPos);
        secondPipe = new Pipe(firstPipe.getTailX() + PIPE_GAP, 0, 22, 60, SCROLL_SPEED, yPos);
        thirdPipe = new Pipe(secondPipe.getTailX() + PIPE_GAP, 0, 22, 60, SCROLL_SPEED, yPos);
    }

    public void update(float delta) {
        frontGrass.update(delta);
        backGrass.update(delta);
        firstPipe.update(delta);
        secondPipe.update(delta);
        thirdPipe.update(delta);

        if (firstPipe.isScrolledLeft()) {
            firstPipe.reset(thirdPipe.getTailX() + PIPE_GAP);
        } else if (secondPipe.isScrolledLeft()) {
            secondPipe.reset(firstPipe.getTailX() + PIPE_GAP);
        } else if (thirdPipe.isScrolledLeft()) {
            thirdPipe.reset(secondPipe.getTailX() + PIPE_GAP);
        }

        if (frontGrass.isScrolledLeft()) {
            backGrass.reset(frontGrass.getTailX());
        } else if (backGrass.isScrolledLeft()) {
            frontGrass.reset(backGrass.getTailX());
        }
    }

    public void stop() {
        backGrass.stop();
        frontGrass.stop();
        firstPipe.stop();
        secondPipe.stop();
        thirdPipe.stop();
    }

    public boolean collides(Bird bird) {
        return (firstPipe.collides(bird) || secondPipe.collides(bird) || thirdPipe.collides(bird));
    }

    public Grass getFrontGrass() {
        return frontGrass;
    }

    public Grass getBackGrass() {
        return backGrass;
    }

    public Pipe getFirstPipe() {
        return firstPipe;
    }

    public Pipe getSecondPipe() {
        return secondPipe;
    }

    public Pipe getThirdPipe() {
        return thirdPipe;
    }
}
