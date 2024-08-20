/*
 * This is the main class that deals with all the drawing, handling, and controlling of our gardens and what they are growing
 * 
 * ECO POINTS HERE FOR USE OF SMART INTELLIGENT FSM, AND FOR DYANMICALY DRAWING IMAGES USING ARRAYS AND SPRITES
 */

package garden;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import sidebar.SidebarTool;
import util.ImageLoader;
import util.Sound;

public class Dirt extends GardenObject {
    private int state = 0;
    private int vegetableState = 0;
    private String vegetable;
    private BufferedImage currentImage;
    private int dateOfPlant;
    private int daysToGrow;
    private int currentDay = 0;
    private int growthStage = 0;
    private boolean imageUpdated = false;
    private Sparkle cloud = null;
    private boolean wasColliding; // Track the previous collision state
    
    // array of images for each vegetable
    private BufferedImage[] carrotImages = new BufferedImage[5];
    private BufferedImage[] tomatoImages = new BufferedImage[5];
    private BufferedImage[] cornImages = new BufferedImage[5];
    private BufferedImage[] lettuceImages = new BufferedImage[5];

    public Dirt(double x, double y, double s) {
        super(x, y, s);
        img = ImageLoader.loadImage("assets/dirt.png");
        loadImages();
    }

    private void loadImages() {
        carrotImages[0] = ImageLoader.loadImage("assets/carrot/carrot3.png");
        carrotImages[1] = ImageLoader.loadImage("assets/carrot/carrot2.png");
        carrotImages[2] = ImageLoader.loadImage("assets/carrot/carrot1.png");
        carrotImages[3] = ImageLoader.loadImage("assets/carrot/carrot4.png");
        carrotImages[4] = ImageLoader.loadImage("assets/carrot/carrot5.png");

        tomatoImages[0] = ImageLoader.loadImage("assets/tomato/tomato3.png");
        tomatoImages[1] = ImageLoader.loadImage("assets/tomato/tomato2.png");
        tomatoImages[2] = ImageLoader.loadImage("assets/tomato/tomato1.png");
        tomatoImages[3] = ImageLoader.loadImage("assets/tomato/tomato4.png");
        tomatoImages[4] = ImageLoader.loadImage("assets/tomato/tomato5.png");

        cornImages[0] = ImageLoader.loadImage("assets/corn/corn1.png");
        cornImages[1] = ImageLoader.loadImage("assets/corn/corn2.png");
        cornImages[2] = ImageLoader.loadImage("assets/corn/corn3.png");
        cornImages[3] = ImageLoader.loadImage("assets/corn/corn4.png");
        cornImages[4] = ImageLoader.loadImage("assets/corn/corn5.png");

        lettuceImages[0] = ImageLoader.loadImage("assets/lettuce/lettuce1.png");
        lettuceImages[1] = ImageLoader.loadImage("assets/lettuce/lettuce2.png");
        lettuceImages[2] = ImageLoader.loadImage("assets/lettuce/lettuce3.png");
        lettuceImages[3] = ImageLoader.loadImage("assets/lettuce/lettuce4.png");
        lettuceImages[4] = ImageLoader.loadImage("assets/lettuce/lettuce5.png");
    }

    @Override
    public void update() {
        if (vegetableState == 0) {
            vegetable = "none";
            imageUpdated = false;
            currentImage = null;
        } else {
            updateVegetableDetails();
            calculateGrowthStage(currentDay);
        }

        // create the sparkles if we get to the final stage of growth to indicate done
        if(growthStage == 3){
            cloud = new Sparkle(pos.x, pos.y);
        }else if(growthStage == 4){
            cloud = null;
        }

        updateCurrentImage();
    }

    private void updateVegetableDetails() {
        // FSM for controlling which vegetable is grown and what we need to draw and flick through
        switch (vegetableState) {
            case 1:
                vegetable = "carrot";
                daysToGrow = 2;
                break;
            case 2:
                vegetable = "tomato";
                daysToGrow = 3;
                break;
            case 3:
                vegetable = "corn";
                daysToGrow = 4;
                break;
            case 4:
                vegetable = "lettuce";
                daysToGrow = 2;
                break;
        }

        if (!imageUpdated) {
            currentImage = getInitialImageForVegetable();
            imageUpdated = true;
        }
    }

    private BufferedImage getInitialImageForVegetable() {
        // depending on the FSM above we will start with the initial draw image of the vege
        switch (vegetable) {
            case "carrot":
                return carrotImages[0];
            case "tomato":
                return tomatoImages[0];
            case "corn":
                return cornImages[0];
            case "lettuce":
                return lettuceImages[0];
            default:
                return null;
        }
    }

    // method for calculation how many days it has been growing and what stage we are at of growth
    private void calculateGrowthStage(int currentDay) {
        int daysLeftToGrow = daysToGrow - (currentDay - dateOfPlant);
        if(growthStage < 4){
            if(daysLeftToGrow >= 3){
                growthStage = 0;
            }else if(daysLeftToGrow == 2){
                growthStage = 1;
            }else if(daysLeftToGrow == 1){
                growthStage = 2;
            }else if(daysLeftToGrow == 0){
                growthStage = 3;
            }
        }else if(growthStage == 4){
            if(daysLeftToGrow <= -1){
                resetDirt();
            }
        }
    }

    private void updateCurrentImage() {
        // draw and switch the images of the vege based on the growth stage
        switch (vegetable) {
            case "carrot":
                currentImage = carrotImages[growthStage];
                break;
            case "tomato":
                currentImage = tomatoImages[growthStage];
                break;
            case "corn":
                currentImage = cornImages[growthStage];
                break;
            case "lettuce":
                currentImage = lettuceImages[growthStage];
                break;
        }
    }

    public void setVegetableState(int state) {
        vegetableState = state;
        growthStage = 0;
        imageUpdated = false;
    }

    @Override
    public void drawObject(Graphics2D g2) {
        AffineTransform transform = g2.getTransform();
        g2.translate(pos.x, pos.y);
        g2.scale(sca, sca);

        g2.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, null);

        if (currentImage != null && vegetableState != 0) {
            g2.drawImage(currentImage, -currentImage.getWidth() / 2, -currentImage.getHeight() / 2, null);
        }

        g2.setTransform(transform);

        if(cloud != null){
            cloud.drawSparkle(g2);
        }
    }

    // method to handle the state and wether or not we can plant 
    public void setDirtImg(int dirtState) {
        if (dirtState == 0) {
            this.state = 0;
            img = ImageLoader.loadImage("assets/dirt.png");
            imageUpdated = false;
        } else if (dirtState == 1) {
            this.state = 1;
            img = ImageLoader.loadImage("assets/dirtPlant.png");
        }
    }

    public int getDirtState() {
        return state;
    }

    @Override
    protected void setOutlineShape() {
        areaShape = new Rectangle2D.Double(-img.getWidth() / 2, -img.getHeight() / 2, img.getWidth(), img.getHeight());
    }

    public int getGrowthStage() {
        return growthStage;
    }

    public void setGrowthStage(int i) {
        growthStage = i;
    }

    public int getVegetableState() {
        return vegetableState;
    }

    public void setDayOfPlant(int day) {
        dateOfPlant = day;
    }

    public void addDay() {
        currentDay++;
    }


    private void resetDirt() {
        state = 0;
        vegetableState = 0;
        vegetable = "none";
        currentImage = null;
        dateOfPlant = 0;
        daysToGrow = 0;
        currentDay = 0;
        growthStage = 0;
        imageUpdated = false;
        cloud = null;
        img = ImageLoader.loadImage("assets/dirt.png");
    }

    @Override
    public boolean isColliding(SidebarTool tool) {
        boolean isColliding = getOutline().intersects(tool.getBoundingBox()) &&
                              tool.getOutline().intersects(getBoundingBox());
        
        if (isColliding && !wasColliding && state == 0 && vegetableState == 0) {
            Sound.play("assets/netherwart4.wav");
        }else if(isColliding && !wasColliding && state == 1 && vegetableState == 0){
            Sound.play("assets/grass2.wav");
        }else if(isColliding && !wasColliding && state == 1 && growthStage == 3){
            Sound.play("assets/successful_hit.wav");
        }
        
        wasColliding = isColliding;

        return isColliding;
    }
}
