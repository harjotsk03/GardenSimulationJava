package creationFactory;

import java.util.ArrayList;

import fenceDecorator.FenceDecorInterface;
import fenceDecorator.SimpleFence;
import garden.Fence;
import garden.Garden;
import garden.GardenObject;
import sidebar.Carrot;
import sidebar.Corn;
import sidebar.Digger;
import sidebar.Lettuce;
import sidebar.Shovel;
import sidebar.SidebarTool;
import sidebar.SimpleSidebar;
import sidebar.Tomato;
import sidebar.WaterCan;
import ui.ConfirmScreen;
import ui.IconButton;
import ui.InstructionScreen;
import ui.Instructions;
import ui.IntoScreen;
import ui.PausedScreen;
import ui.ScreenClass;
import ui.VegeShow;

public class CreatorFactory {
    
    public SidebarTool createSidebarOject(String type, int x, int y, double scale){
        SidebarTool sideBarObject = null;
        if(type == "pick"){
            sideBarObject = new Shovel(x, y, scale);
        }else if(type == "tomato"){
            sideBarObject = new Tomato(x, y, scale);
        }else if(type == "carrot"){
            sideBarObject = new Carrot(x, y, scale);
        }else if(type == "lettuce"){
            sideBarObject = new Lettuce(x, y, scale);
        }else if(type == "corn"){
            sideBarObject = new Corn(x, y, scale);
        }else if(type == "watercan"){
            sideBarObject = new WaterCan(x, y, scale);
        }else if(type == "digger"){
            sideBarObject = new Digger(x, y, scale);
        }

        return sideBarObject;
    }

    public GardenObject createGardenObject(String type, int x, int y, double scale){
        return null;
    }

    public ScreenClass createScreen(String type, int x, int y){
        ScreenClass screen = null;

        if(type == "intro"){
            screen = new IntoScreen(x, y);
        }else if(type == "paused"){
            screen = new PausedScreen(x, y);
        }else if(type == "instruction"){
            screen = new InstructionScreen(x, y);
        }else if(type == "confirm"){
            screen = new ConfirmScreen(x, y);
        }

        return screen;
    }

    public Garden createGarden(String img){
        Garden garden;
        garden = new Garden("assets/garden.png");
        return garden;
    }

    public Instructions createInstruction(String instruction, String icon, int x, int y){
        return null;
    }

    public SimpleSidebar createSideBar(int x, int y, double scale){
        SimpleSidebar sidebar;
        return sidebar = new SimpleSidebar(x, y, scale);
    }

    public IconButton createIconButton(int x, int y, String text, String type){
        IconButton button = null;

        if(type == "emptyFence"){
            button = new IconButton(x,y, text);
        }else if(type == "addWood"){
            button = new IconButton(x,y, text);
        }else if(type == "addBags"){
            button = new IconButton(x,y, text);
        }else if(type == "fullFence"){
            button = new IconButton(x,y, text);
        }else if(type == "sellButton"){
            button = new IconButton(x,y, text);
        }else if(type == " "){
            button = new IconButton(x,y, text);
        }

        return button;
    }

    public FenceDecorInterface createFenceDecorObect(int x, int y){
        FenceDecorInterface fence = null;
        fence = new SimpleFence(x, y);
        return fence;
    }

    public ArrayList createArrayList(){
        ArrayList arrayList = new ArrayList<>();

        return arrayList;
    }

    public VegeShow createVegeUI(int x, int y, int scale){
        VegeShow showUI = null;

        showUI = new VegeShow(x, y, scale);

        return showUI;
    }
}
