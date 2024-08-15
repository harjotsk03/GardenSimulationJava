package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.Timer;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;

import creationFactory.CreatorFactory;
import ddf.minim.Minim;
import fenceDecorator.BagDecor;
import fenceDecorator.SimpleFence;
import fenceDecorator.WoodDecor;
import fenceDecorator.FenceDecorInterface;
import garden.Garden;
import garden.Sparkle;
import sidebar.Carrot;
import sidebar.Corn;
import sidebar.Digger;
import sidebar.Lettuce;
import sidebar.Shovel;
import sidebar.SidebarTool;
import sidebar.SimpleSidebar;
import sidebar.Tomato;
import sidebar.WaterCan;
import garden.Dirt;
import garden.Fence;
import ui.Alert;
import ui.Button;
import ui.ConfirmScreen;
import ui.EndingScreen;
import ui.IconButton;
import ui.InstructionScreen;
import ui.Instructions;
import ui.IntoScreen;
import ui.PausedScreen;
import ui.ScreenClass;
import ui.SellingScreen;
import ui.Sun;
import ui.VegeShow;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import util.MinimHelper;
import util.Sound;
import util.Util;

public class GardenPanel extends JPanel implements ActionListener {
    public final static int W_WIDTH = 1280;
    public final static int W_HEIGHT = 832;
    

    private double mouseX;
    private double mouseY;

    private CreatorFactory creatorFactory;

    private Garden garden;
    private ArrayList<Dirt> DirtArr;
    private ArrayList<Fence> FenceArr;

    private Instructions instructions = null;
    private SimpleSidebar sidebar;
    private SidebarTool pick;
    private SidebarTool tomato;
    private SidebarTool carrot;
    private SidebarTool digger;
    private SidebarTool corn;
    private SidebarTool lettuce;
    private SidebarTool waterCan;
    private Sparkle cloud = null;
    private Sun sun;

    private int sidebarX = W_WIDTH - 60;
    private int sidebarYStart = 200;
    private int spacing = 75;

    private int light = 0;
    private boolean isDay = true;
    private int timeOfDay = 600;
    private Timer timer;
    private int day = 1;

    private VegeShow showUIArea;
    private Alert alert = null;

    private int gameState = 0;

    private boolean hasWatered = false;
    private boolean hasDug = false;
    private boolean hasPlanted = false;
    private boolean toolActive = false;
    private boolean readyToHarvest = false;

    private int carrotReady;
    private int lettuceReady;
    private int cornReady;
    private int tomatoReady;

    FenceDecorInterface fence;
    private IconButton emptyFenceButton;
    private IconButton woodDecorButton;
    private IconButton bagsDecorButton;
    private IconButton fullFenceButton;

    private boolean seePrices = false;

    private IconButton sellButton = null;

    private IconButton beginGame;

    private boolean drawSparkle = false;

    @SuppressWarnings("unused")
    private Minim minim;

    private ArrayList<ScreenClass> screens;

    private ScreenClass introScreen;
    private ScreenClass pausedScreen;

    private boolean keyPressed = false;

    private ScreenClass sellingScreen = null;
    private IconButton backButton = null;
    private IconButton increaseCarrot = null;
    private IconButton increaseLettuce = null;
    private IconButton increaseCorn = null;
    private IconButton increaseTomato = null;
    private IconButton decreaseCarrot = null;
    private IconButton decreaseLettuce = null;
    private IconButton decreaseCorn = null;
    private IconButton decreaseTomato = null;
    private IconButton sellCropsButton = null;
    private int carrotsSell = 0;
    private int lettuceSell = 0;
    private int cornSell = 0;
    private int tomatoSell = 0;
    private double carrotPrice = 0.80;
    private double lettucePrice = 1.00;
    private double cornPrice = 2.10;
    private double tomatoPrice = 1.60;
    private double totalPrice = 0.00;
    private double moneyMade = 0.00;

    private IconButton endScreenPlayAgainButton;
    private IconButton endScreenContinueButton;

    private ScreenClass instructionScreen;
    private ScreenClass confirmScreen;
    private ScreenClass endingScreen;

    private boolean makePriceSound = false;

    private Instructions carrotPricesShow;
    private Instructions tomatoPricesShow;
    private Instructions cornPricesShow;
    private Instructions lettucePricesShow;

    private IconButton helpButton;
    private IconButton closeHelpButton;

    private Font customFont;

    private JFrame frame;

    GardenPanel(JFrame frame) {
        this.frame = frame;
        this.setBackground(Color.white);
        setPreferredSize(new Dimension(W_WIDTH, W_HEIGHT));

        loadCustomFont();

        creatorFactory = new CreatorFactory();

        garden = creatorFactory.createGarden("assets/garden.png");

        sidebar = creatorFactory.createSideBar(W_WIDTH - 60, W_HEIGHT / 2, 1);

        pick = creatorFactory.createSidebarOject("pick", sidebarX, sidebarYStart, 0.7);
        tomato = creatorFactory.createSidebarOject("tomato", sidebarX, sidebarYStart + spacing, 0.9);
        carrot = creatorFactory.createSidebarOject("carrot", sidebarX, sidebarYStart + (2 * spacing), 1);
        lettuce = creatorFactory.createSidebarOject("lettuce", sidebarX, sidebarYStart + (3 * spacing), 1);
        corn = creatorFactory.createSidebarOject("corn", sidebarX, sidebarYStart + (4 * spacing), 1);
        waterCan = creatorFactory.createSidebarOject("watercan", sidebarX, sidebarYStart + (5 * spacing), 1);
        digger = creatorFactory.createSidebarOject("digger", sidebarX, sidebarYStart + (6 * spacing), 0.7);

        showUIArea = creatorFactory.createVegeUI(W_WIDTH - 250, 25, 1);
        sun = new Sun();

        DirtArr = creatorFactory.createArrayList();
        FenceArr = creatorFactory.createArrayList();
        screens = creatorFactory.createArrayList();

        helpButton = creatorFactory.createIconButton(20, 20, "Help", "button");
        closeHelpButton = creatorFactory.createIconButton(20, 50, "Close", "button");
        
        beginGame = creatorFactory.createIconButton(W_WIDTH - 130, W_HEIGHT - 60, "BEGIN", "beginButton");

        createGarden(4, 3);

        minim = new Minim(new MinimHelper());

        MyMouseListener ml = new MyMouseListener();
        addMouseListener(ml);

        MyMouseMotionListener mml = new MyMouseMotionListener();
        addMouseMotionListener(mml);
        
        carrotReady = 0;
        lettuceReady = 0;
        cornReady = 0;
        tomatoReady = 0;

        screens.add(creatorFactory.createScreen("intro", W_WIDTH, W_HEIGHT));
        screens.add(creatorFactory.createScreen("instruction", W_WIDTH, W_HEIGHT));
        screens.add(creatorFactory.createScreen("confirm", W_WIDTH, W_HEIGHT));
        screens.add(creatorFactory.createScreen("ending", W_WIDTH, W_HEIGHT));
        
        sellButton = creatorFactory.createIconButton(W_WIDTH - 358 , 27, "Sell Crops", "sellButton");

        emptyFenceButton = creatorFactory.createIconButton(40, W_HEIGHT - 180, "Empty Fence", "emptyFence");
        woodDecorButton = creatorFactory.createIconButton(40, W_HEIGHT - 140, "Add Wood", "addWood");
        bagsDecorButton = creatorFactory.createIconButton(40, W_HEIGHT - 100, "Add Bags", "addBags");
        fullFenceButton = creatorFactory.createIconButton(40, W_HEIGHT - 60, "Full Fence", "fullFence");

        carrotPricesShow = new Instructions(W_WIDTH - 230, 420 - 50, "Carrot\nValue: $0.80/each\nGrowth Time: 3 Days", "shovel");
        lettucePricesShow = new Instructions(W_WIDTH - 230, 540 - 50, "Lettuce\nValue: $1.00/each\nGrowth Time: 3 Days", "shovel");
        cornPricesShow = new Instructions(W_WIDTH - 230, 660 - 50, "Corn\nValue: $2.10/each\nGrowth Time: 5 Days", "shovel");
        tomatoPricesShow = new Instructions(W_WIDTH - 230, 300 - 50, "Tomato\nValue: $1.60/each\nGrowth Time: 4 Days", "shovel");

        fence = creatorFactory.createFenceDecorObect(300, W_HEIGHT - 100);

        

        timer = new Timer(30, this);

        Sound.play("assets/gentle-fields-194622.wav");

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        AffineTransform originalTransform = g2.getTransform();

        if (gameState == 0) {
            for(int i = 0; i < screens.size(); i++){
                if(screens.get(i) instanceof IntoScreen){
                    screens.get(i).drawScreen(g2);
                }
            }
        } else if (gameState == 1) {
            for(int i = 0; i < screens.size(); i++){
                if(screens.get(i) instanceof InstructionScreen){
                    screens.get(i).drawScreen(g2);
                }
            }

            beginGame.drawButton(g2);
        }else if(gameState == 2){

            garden.drawGarden(g2);
            for (Dirt dirt : DirtArr) {
                dirt.drawObject(g2);
            }
            for (Fence fence : FenceArr) {
                fence.drawObject(g2);
            }

            fence.showFence(g2);

            g2.setColor(new Color(0, 0, 0, light));
            g2.fillRect(0, 0, W_WIDTH, W_HEIGHT);

            sidebar.drawSidebar(g2);
            pick.drawObject(g2);
            tomato.drawObject(g2);
            carrot.drawObject(g2);
            lettuce.drawObject(g2);
            corn.drawObject(g2);
            waterCan.drawObject(g2);
            digger.drawObject(g2);        

            emptyFenceButton.drawButton(g2);
            woodDecorButton.drawButton(g2);
            bagsDecorButton.drawButton(g2);
            fullFenceButton.drawButton(g2);

            if(sellButton != null){
                sellButton.drawButton(g2);
            }

            showUIArea.drawButton(g2);
        
            if (instructions != null) {
                instructions.displayInstruction(g2);
            }

            g2.setTransform(originalTransform);

            int hours = timeOfDay / 60;
            int minutes = timeOfDay % 60;

            String timeString = String.format("%02d:%02d", hours, minutes);

            g.setFont(customFont.deriveFont(Font.PLAIN, 20));
            // g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.WHITE);
            int stringWidth = g2.getFontMetrics().stringWidth(timeString);
            g2.drawString(timeString, W_WIDTH - 40 - 50, 60);
            g.setFont(customFont.deriveFont(Font.PLAIN, 12));
            g2.drawString("Day " + day, W_WIDTH - 40 - 50, 78);

            g.setFont(customFont.deriveFont(Font.PLAIN, 20));
            g2.drawString("" + carrotReady, W_WIDTH - 85 - 50, 60);
            g2.drawString("" + lettuceReady, W_WIDTH - 148 - 50, 60);
            g2.drawString("" +  tomatoReady, W_WIDTH - 148 - 50, 100);
            g2.drawString("" + cornReady, W_WIDTH - 85 - 50, 100);

            AffineTransform treeSet = g2.getTransform();
            g2.translate(-100, -100);
            g2.scale(0.5, 0.5);
            // sun.drawSun(g2, 300, 300, 100, 6);
            g2.setTransform(treeSet);

            g2.setTransform(originalTransform);

            if(seePrices){
                carrotPricesShow.displayInstruction(g2);
                tomatoPricesShow.displayInstruction(g2);
                cornPricesShow.displayInstruction(g2);
                lettucePricesShow.displayInstruction(g2);
            }

            helpButton.drawButton(g2);
            

        }else if(gameState == 3){

            sellingScreen.drawScreen(g2);
            backButton.drawButton(g2);
            increaseCarrot.drawButton(g2);
            increaseLettuce.drawButton(g2);
            increaseCorn.drawButton(g2);
            increaseTomato.drawButton(g2);
            decreaseCarrot.drawButton(g2);
            decreaseCorn.drawButton(g2);
            decreaseLettuce.drawButton(g2);
            decreaseTomato.drawButton(g2);

            g.setFont(customFont.deriveFont(Font.PLAIN, 20));
            g2.setColor(Color.black);
            g2.drawString("" + carrotsSell, W_WIDTH / 2 + 65, 310);
            g2.drawString("" + tomatoSell, W_WIDTH / 2 + 65, 370);
            g2.drawString("" + cornSell, W_WIDTH / 2 + 65, 430);
            g2.drawString("" + lettuceSell, W_WIDTH / 2 + 65, 485);
            totalPrice = Math.abs(totalPrice);
            String formattedPrice = String.format("%.2f", totalPrice);
            g2.drawString(formattedPrice, W_WIDTH / 2, 600);
               
        }else if(gameState == 4){
        }else if(gameState == 5){

            for(int i = 0; i < screens.size(); i++){
                if(screens.get(i) instanceof EndingScreen){
                    screens.get(i).drawScreen(g2);
                }
            }              

            g.setFont(customFont.deriveFont(Font.PLAIN, 30));
            moneyMade = Math.abs(moneyMade);
            String formattedMoneyMade = String.format("%.2f", moneyMade);
            g2.drawString("You made a total of " + formattedMoneyMade, W_WIDTH / 2 - 180, W_HEIGHT/2);

            if(endScreenContinueButton != null){

            }

            if(endScreenPlayAgainButton != null){

            }

            endScreenContinueButton.drawButton(g2);
            endScreenPlayAgainButton.drawButton(g2);
        }else if(gameState == 6){
            for(int i = 0; i < screens.size(); i++){
                if(screens.get(i) instanceof InstructionScreen){
                    screens.get(i).drawScreen(g2);
                }
            }
            closeHelpButton.drawButton(g2);
        }

        if(alert != null){
            alert.drawButton(g2);
        }
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {

        if(gameState == 2){
            
            boolean instructionsDisplayed = false;
            
            for(int i = 0; i < DirtArr.size(); i++){
                Dirt currentDirt = DirtArr.get(i);
    
                int counter = 0;
                int readyHarvester = 0;

                if(currentDirt.getGrowthStage() == 3){
                    readyHarvester ++;
                }
    
                if(currentDirt.getDirtState() == 1){
                    counter++;
                }
    
                if(counter > 0){
                    hasDug = true;
                    instructions = null;
                }

                if(readyHarvester > 0 && readyHarvester <= 1){
                    readyToHarvest = true;
                }
            }
            // Show instructions only if not already displayed and if ready to harvest
            if (readyToHarvest && !instructionsDisplayed) {
                instructions = new Instructions(W_WIDTH / 2 + 150, W_HEIGHT - 100, "Grab the harvest tool and harvest your sparkling crops!", "digger");
                instructionsDisplayed = true; // Set the flag to true once instructions are displayed
            }
    
                if (pick.getMouseFollowing()) {
                    pick.setPos(mouseX, mouseY);
                }
                if (tomato.getMouseFollowing()) {
                    tomato.setPos(mouseX, mouseY);
                }
                if (carrot.getMouseFollowing()) {
                    carrot.setPos(mouseX, mouseY);
                }
                if (digger.getMouseFollowing()) {
                    digger.setPos(mouseX, mouseY);
                }
                if (corn.getMouseFollowing()) {
                    corn.setPos(mouseX, mouseY);
                }
                if (lettuce.getMouseFollowing()) {
                    lettuce.setPos(mouseX, mouseY);
                }
                if (waterCan.getMouseFollowing()) {
                    waterCan.setPos(mouseX, mouseY);
                }
        
            for (int i = 0; i < DirtArr.size(); i++) {
                    Dirt currentDirt = DirtArr.get(i);
                    currentDirt.update();
        
                    if (currentDirt.getDirtState() == 0) {
                        if (currentDirt.isColliding(pick)) {
                            currentDirt.setDirtImg(1);
                        }
                    }
        
                    if (currentDirt.getGrowthStage() == 3) {
                        if (currentDirt.isColliding(digger)) {
                            currentDirt.setGrowthStage(4);
                            readyToHarvest = false;
                            if(currentDirt.getVegetableState() == 1){
                                carrotReady ++;
                            }else if(currentDirt.getVegetableState() == 2){
                                tomatoReady ++;
                            }else if(currentDirt.getVegetableState() == 3){
                                cornReady ++;
                            }else if(currentDirt.getVegetableState() == 4){
                                lettuceReady ++;
                            }
                        }
                    }
        
                    if (currentDirt.getDirtState() == 1) {
                        if (currentDirt.isColliding(carrot)) {
                            currentDirt.setVegetableState(1);
                            currentDirt.setDayOfPlant(day);
                        } else if (currentDirt.isColliding(tomato)) {
                            currentDirt.setVegetableState(2);
                            currentDirt.setDayOfPlant(day);
                        } else if (currentDirt.isColliding(corn)) {
                            currentDirt.setVegetableState(3);
                            currentDirt.setDayOfPlant(day);
                        } else if (currentDirt.isColliding(lettuce)) {
                            currentDirt.setVegetableState(4);
                            currentDirt.setDayOfPlant(day);
                        }
                    }
                }
            
    
            for(int i=0; i < DirtArr.size(); i++){
                Dirt currentDirt = DirtArr.get(i);
                int plantedPlaces = 0;
                if(currentDirt.getVegetableState() != 0){
                    plantedPlaces++;
                }
    
                if(plantedPlaces != 0){
                    hasPlanted = true;
                }
            }
    
            if (light >= 200) {
                isDay = false;
            } else if (light < 0) {
                isDay = true;
            }
    
    
            int incr = 1;
            if (timeOfDay >= 1260 || timeOfDay < 240) {
                incr = 5;
                light+= 3;
                hasWatered = false;
            } else {
                light-= 3;
            }
        
            if (light > 255) {
                light = 255;
            } else if (light < 0) {
                light = 0;
            }
    
            if(!hasDug){
                instructions = new Instructions(W_WIDTH / 2 + 150, W_HEIGHT - 100, "Grab the shovel and dig the mud to prepare for planting!", "shovel");
                timeOfDay = timeOfDay;
            }
            else if(!hasWatered && hasPlanted && timeOfDay == 840){
                instructions = new Instructions(W_WIDTH / 2 + 150, W_HEIGHT - 100, "Water your garden!", "waterCan");
                timeOfDay = timeOfDay;
            }else if(!hasPlanted){
                instructions = new Instructions(W_WIDTH / 2 + 150, W_HEIGHT - 100, "Pick any vegetable and hover over\n the dug up places to plant!", "carrots");
                timeOfDay = timeOfDay;
            }
            else{
                timeOfDay = (timeOfDay + incr) % (24 * 60);
            }

            if(carrotReady > 12 || tomatoReady > 12 || cornReady > 12 || lettuceReady > 12){
                alert = new Alert(W_WIDTH/2 - 200, 50, "You can now sell your crops!");
            }
    
            if(timeOfDay == 0){
                day++;
                for(int i = 0; i < DirtArr.size();  i++){
                    DirtArr.get(i).addDay();
                }
            }
        }

        if(seePrices && !makePriceSound){
            Sound.play("assets/ladder1.wav");
            makePriceSound = true;
        }

        repaint();
    }

    public class MyMouseListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();

            if(gameState == 1){
                if(beginGame.clicked(mouseX, mouseY)){
                    Sound.play("assets/pop.wav");
                    gameState = 2;
                    timer.start();
                    repaint();
                }
            }
        
            if(gameState==0){
                for(int i = 0 ; i < screens.size(); i++){
                   if(screens.get(i) instanceof IntoScreen) {
                    if(screens.get(i).clicked(mouseX, mouseY)){
                        Sound.play("assets/pop.wav");
                        gameState = 1;
                        repaint();
                    }
                   }
                }
            }else if(gameState == 1){
                for(int i = 0 ; i < screens.size(); i++){
                   if(screens.get(i) instanceof InstructionScreen) {
                    if(screens.get(i).clicked(mouseX, mouseY)){
                        Sound.play("assets/pop.wav");
                        gameState = 2;
                        timer.start();
                        repaint();
                    }
                   }
                }
            }

            if(sellButton != null){
                if(sellButton.clicked(mouseX,mouseY)){
                    if(carrotReady > 12 || lettuceReady > 12 || tomatoReady > 12 || cornReady > 12){
                        Sound.play("assets/pop.wav");
                        createSellingScreen(carrotReady, lettuceReady, cornReady, tomatoReady);
                        gameState = 3;
                        timer.stop();
                        repaint();
                    }else{
                        Sound.play("assets/pop.wav");
                        alert = new Alert(W_WIDTH/2 - 250, 50, "You must have 12 or more crops first!");
                    }        
                }
            }

            if(emptyFenceButton.clicked(mouseX, mouseY)){
                Sound.play("assets/pop.wav");
                fence = new SimpleFence(300, W_HEIGHT - 100);
            }else if(woodDecorButton.clicked(mouseX, mouseY)){
                Sound.play("assets/pop.wav");
                FenceDecorInterface baseFence = new SimpleFence(300, W_HEIGHT - 100);
                fence = new WoodDecor(300, W_HEIGHT - 100, baseFence);
            }else if(bagsDecorButton.clicked(mouseX, mouseY)){
                Sound.play("assets/pop.wav");
                FenceDecorInterface baseFence = new SimpleFence(300, W_HEIGHT - 100);
                fence = new BagDecor(300, W_HEIGHT - 100, baseFence);
            }else if(fullFenceButton.clicked(mouseX, mouseY)){
                Sound.play("assets/pop.wav");
                FenceDecorInterface baseFence = new BagDecor(300, W_HEIGHT - 100, new SimpleFence(300, W_HEIGHT - 100));
                fence = new WoodDecor(300, W_HEIGHT - 100, baseFence);
            }

            if(gameState == 4){
                for(int i = 0; i < screens.size(); i++){
                    if(screens.get(i) instanceof ConfirmScreen){
                        if(confirmScreen.confirmClick(mouseX, mouseY)){
                            Sound.play("assets/pop.wav");
                            restartApplication();
                        }else if(confirmScreen.cancelClick(mouseX, mouseY)){
                            Sound.play("assets/pop.wav");
                            gameState = 2;
                            repaint();
                        }
                    }
                }  
            }

            if(gameState == 3){

                if(backButton.clicked(mouseX, mouseY)){
                    Sound.play("assets/pop.wav");
                    gameState = 2;
                    timer.start();
                    repaint();
                }

                if(increaseCarrot.clicked(mouseX, mouseY)){
                    if(carrotsSell >= 0 && carrotsSell < carrotReady){
                        Sound.play("assets/pop.wav");
                        carrotsSell++;
                        totalPrice += carrotPrice;
                        repaint();
                    }
                }
                if(decreaseCarrot.clicked(mouseX, mouseY)){
                    if(carrotsSell > 0 && carrotsSell <= carrotReady){
                        Sound.play("assets/pop.wav");
                        carrotsSell--;
                        totalPrice -= carrotPrice;
                        repaint();
                    }
                }

                // TOMATO
                if(increaseTomato.clicked(mouseX, mouseY)){
                    if(tomatoSell >= 0 && tomatoSell < tomatoReady){
                        Sound.play("assets/pop.wav");
                        tomatoSell++;
                        totalPrice += tomatoPrice;
                        repaint();
                    }
                }
                if(decreaseTomato.clicked(mouseX, mouseY)){
                    if(tomatoSell > 0 && tomatoSell <= tomatoReady){
                        Sound.play("assets/pop.wav");
                        tomatoSell--;
                        totalPrice -= tomatoPrice;
                        repaint();
                    }
                }

                // CORN
                if(increaseCorn.clicked(mouseX, mouseY)){
                    if(cornSell >= 0 && cornSell < cornReady){
                        Sound.play("assets/pop.wav");
                        cornSell++;
                        totalPrice += cornPrice;
                        repaint();
                    }
                }
                if(decreaseCorn.clicked(mouseX, mouseY)){
                    if(cornSell > 0 && cornSell <= cornReady){
                        Sound.play("assets/pop.wav");
                        cornSell--;
                        totalPrice -= cornPrice;
                        repaint();
                    }
                }

                // LETTUCE
                if(increaseLettuce.clicked(mouseX, mouseY)){
                    if(lettuceSell >= 0 && lettuceSell < lettuceReady){
                        Sound.play("assets/pop.wav");
                        lettuceSell++;
                        totalPrice += lettucePrice;
                        repaint();
                    }
                }
                if(decreaseLettuce.clicked(mouseX, mouseY)){
                    if(lettuceSell > 0 && lettuceSell <= lettuceReady){
                        Sound.play("assets/pop.wav");
                        lettuceSell--;
                        totalPrice -= lettucePrice;
                        repaint();
                    }
                }         
                
                if(sellingScreen.clicked(mouseX, mouseY)){
                    Sound.play("assets/pop.wav");
                    carrotReady -= carrotsSell;
                    lettuceReady -= lettuceSell;
                    cornReady -= cornSell;
                    tomatoReady -= tomatoSell;
                    gameState = 5;
                    moneyMade = totalPrice;
                    endScreenPlayAgainButton = creatorFactory.createIconButton(375, 500, "Play Again", "endScreen");
                    endScreenContinueButton = creatorFactory.createIconButton(670, 500, "Continue", "endScreen");
                    repaint();
                }
            }

            if(gameState == 5){
                if(endScreenContinueButton.clicked(mouseX, mouseY)){
                    Sound.play("assets/pop.wav");
                    gameState = 2;
                    timer.start();
                    repaint();
                }

                if(endScreenPlayAgainButton.clicked(mouseX, mouseY)){
                    Sound.play("assets/pop.wav");
                    restartApplication();
                }
            }

            if(gameState == 2){
                if(helpButton.clicked(mouseX, mouseY)){
                    gameState = 6;
                    timer.stop();
                    repaint();
                }
            }

            if(gameState == 6){
                if(closeHelpButton.clicked(mouseX,mouseY)){
                    gameState = 2;
                    timer.start();
                    repaint();
                }
            }
        
            if (gameState == 2) {
                if (waterCan.clicked(mouseX, mouseY)) {
                    hasWatered = true;
                    instructions = null;
                }

                if(e.isControlDown()){
                    seePrices = !seePrices;
                    if(makePriceSound){
                        makePriceSound = false;
                    }
                }
        
                moveTool(pick, e, sidebarYStart);
                moveTool(tomato, e, sidebarYStart + spacing);
                moveTool(carrot, e, sidebarYStart + (2 * spacing));
                moveTool(lettuce, e, sidebarYStart + (3 * spacing));
                moveTool(corn, e, sidebarYStart + (4 * spacing));
                moveTool(waterCan, e, sidebarYStart + (5 * spacing));
                moveTool(digger, e, sidebarYStart + (6 * spacing));
            }
        }
    }

    public class MyMouseMotionListener extends MouseMotionAdapter {
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }
    }




    private void createGarden(int col, int row) {
        int widthBetween = 250;
        int heightBetween = 200;

        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                Util.generateGarden(110 + (i * widthBetween), 115 + (j * heightBetween), DirtArr, 160 + (i * widthBetween), 145 + (j * heightBetween), FenceArr);
            }
        }

    }

    private void moveTool(SidebarTool object, MouseEvent e, float height) {
        if (object.clicked(mouseX, mouseY)) {
            if (e.isShiftDown()) {
                toolActive = false;
                Sound.play("assets/ladder2.wav");
                object.setMouseFollowing(false);
                object.setPos(W_WIDTH - 60, height);
            } else {
                if(!toolActive){
                    toolActive = true;
                    Sound.play("assets/ladder1.wav");
                    object.setMouseFollowing(true);
                    object.setPos(mouseX, mouseY);
                }
            }
        }
    }

    private void restartApplication() {
        frame.dispose();

        JFrame newFrame = new JFrame("Garden Game");
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(W_WIDTH, W_HEIGHT);
        newFrame.setResizable(false);
        newFrame.add(new GardenPanel(newFrame));
        newFrame.setVisible(true);
    }

    private void createSellingScreen(int carrotReady, int lettuceReady, int cornReady, int tomatoReady){
        sellingScreen = creatorFactory.createScreen("selling", W_WIDTH, W_HEIGHT);

        this.carrotReady = carrotReady;
        this.lettuceReady = lettuceReady;
        this.cornReady = cornReady;
        this.tomatoReady = tomatoReady;

        backButton = creatorFactory.createIconButton(50, 50, "Back", "backButton");
        increaseCarrot = creatorFactory.createIconButton(W_WIDTH /2 + 100, 285, "+", "increase");
        increaseLettuce = creatorFactory.createIconButton(W_WIDTH /2 + 100, 460, "+", "increase");
        increaseCorn = creatorFactory.createIconButton(W_WIDTH /2 + 100, 405, "+", "increase");
        increaseTomato = creatorFactory.createIconButton(W_WIDTH /2 + 100, 345, "+", "increase");
        decreaseCarrot = creatorFactory.createIconButton(W_WIDTH /2 - 10, 285, "-", "decrease");
        decreaseLettuce = creatorFactory.createIconButton(W_WIDTH /2 - 10, 460, "-", "decrease");
        decreaseCorn = creatorFactory.createIconButton(W_WIDTH /2 - 10, 405, "-", "decrease");
        decreaseTomato = creatorFactory.createIconButton(W_WIDTH /2 - 10, 345, "-", "decrease");
    }

    private void loadCustomFont() {
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("assets/IrishGrover-Regular.ttf")).deriveFont(40f);
            
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

}
