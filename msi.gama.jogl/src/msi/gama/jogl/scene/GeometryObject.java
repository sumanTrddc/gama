package msi.gama.jogl.scene;

import java.awt.Color;
import msi.gama.common.util.GuiUtils;
import msi.gama.jogl.utils.JOGLAWTGLRenderer;
import msi.gama.metamodel.agent.IAgent;
import msi.gama.metamodel.shape.GamaPoint;
import com.vividsolutions.jts.geom.Geometry;

public class GeometryObject extends AbstractObject implements Cloneable {

	public Geometry geometry;
	public IAgent agent;
	public double z_layer;
	public int layerId;
	public String type;
	public Boolean fill = true;
	public Color border;
	public Boolean isTextured;
	public int angle;
	public double height;
	public double altitude;
	public boolean rounded;
	
	public GeometryObject(Geometry geometry, IAgent agent, double z_layer, int layerId, Color color, Double alpha,
		Boolean fill, Color border, Boolean isTextured, int angle, double height, GamaPoint offset, GamaPoint scale,
		boolean rounded, String type) {
	    super(color, offset, scale, alpha);
	    if (type.compareTo("gridLine") == 0){
	    	this.fill = false;
	    }
	    /*The z_fight value must be a unique value so the solution has been to make the hypothesis that
    	a layer has less than 1 000 000 agent to make a unique z-fighting value per agent.*/
	    if((agent !=null && (agent.getLocation().getZ() == 0 ) && (height == 0 ))){
	    	Double z_fight = Double.parseDouble(layerId +"."+ agent.getIndex());
	    	setZ_fighting_id(z_fight);
	    	//setZ_fighting_id((double) (layerId *10 + agent.getIndex()));
	    	//System.out.println("z-fighting" + (double) (layerId *1000000 + agent.getIndex()));
	    }
		this.geometry = geometry;
		this.agent = agent;
		this.z_layer = z_layer;
		this.layerId = layerId;
		this.type = type;
		this.fill = fill;
		this.border = border;
		this.isTextured = false;
		this.angle = angle;
		this.height = height;
		this.altitude = 0.0f;
		this.rounded = rounded;
	}

	@Override
	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (CloneNotSupportedException cnse) {
			cnse.printStackTrace(System.err);
		}
		return o;
	}

	@Override
	public void unpick() {
		picked = false;
	}

	public void pick() {
		picked = true;
	}

	@Override
	public Color getColor() {
		if ( picked ) {
			return pickedColor;
		}
		return super.getColor();
	}

	@Override
	public void draw(final ObjectDrawer drawer, final boolean picking) {
		if ( picking ) {
			JOGLAWTGLRenderer renderer = drawer.renderer;
			renderer.gl.glPushMatrix();
			renderer.gl.glLoadName(pickingIndex);
			if ( renderer.pickedObjectIndex == pickingIndex ) {
				if ( agent != null /* && !picked */) {
					renderer.setPicking(false);
					pick();
					renderer.currentPickedObject = this;
					renderer.displaySurface.selectAgents(0, 0, agent, layerId - 1);
				}
			}
			super.draw(drawer, picking);
			renderer.gl.glPopMatrix();
		} else {
			super.draw(drawer, picking);
		}
	}
}
