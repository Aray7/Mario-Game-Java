import java.util.Iterator;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;

public class Brick extends Sprite
{
	Brick(int xx, int yy, int ww, int hh, Model m)
	{
		super(m);
		prev_x = 0;
		prev_y = 0;
		x = xx;
		y = yy;
		w = ww;
		h = hh;
		vvel = 0;
		groundFrames = 0;
		type = "Brick";
	}

	Brick(Json ob, Model m)
	{
		 super(m);
		 type = (String)ob.getString("type");
         x = (int)ob.getLong("x");
		 y = (int)ob.getLong("y");
		 w = (int)ob.getLong("w");
		 h = (int)ob.getLong("h");
		 vvel = (double)ob.getDouble("vvel");
		 m.addBrick(x, y, w, h);
	}

	void update()
	{
		Iterator<Sprite> it = model.sprites.iterator();
		while(it.hasNext())
		{
			Sprite s = it.next();
			if(s.type == "Mario")
			{
				if(doesCollide(s))
					push_out(s);
			}
		}
	}

	void draw(Graphics g)
	{
		g.setColor(new Color(0, 0, 0));
		g.drawRect(x - model.scrollPos, y, w, h);
	}

	void push_out(Sprite that)
	{
		if(that.y + that.h >= this.y && that.prev_y + that.h <= this.y)// coming from the top
		{
			that.vvel = 0;
			that.y = this.y - that.h;
			that.groundFrames = 0;
		}

		else if(that.y <= this.y + this.h && that.prev_y >= this.y + that.h) //coming from the bottom.
		{
			that.y = this.y + this.h;
			that.vvel = 1;
		}

		else if(that.x + that.w >= this.x && that.prev_x + that.w <= this.x) //coming in from the left
		{
			that.x = this.x - that.w;
		}

		else if( that.x <= this.x + this.w && that.prev_x >= this.x + this.w)
		{
			that.x = this.x + this.w;
		}
	}

	boolean doesCollide(Sprite that)
	{
		if (this.x + this.w <= that.x)
			return false;
		if (this.x >= that.x + that.w)
			return false;
		if (this.y + this.h <= that.y)
			return false;
		if (this.y >= that.y + that.h)
			return false;
		return true;
	}

	void remember_pos()
	{

	}

	Json marshall()
    {
         Json ob = Json.newObject();
         ob.add("type", type);
         ob.add("x", x);
		 ob.add("y", y);
		 ob.add("w", w);
		 ob.add("h", h);
		 ob.add("vvel", vvel);
         return ob;
    }

}
