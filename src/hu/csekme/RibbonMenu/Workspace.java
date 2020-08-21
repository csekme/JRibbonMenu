package hu.csekme.RibbonMenu;
import java.util.ArrayList;
import java.util.List;

public class Workspace {

		List<Layer> layers;
		
		public Workspace() {
			layers = new  ArrayList<>();
		}
		
		public Layer addLayer() {
			Layer layer = new Layer();
			layers.add(layer);
			return layer;
		}
}
