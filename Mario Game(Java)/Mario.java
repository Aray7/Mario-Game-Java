import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;

public class Mario extends Sprite
{
	int MarioFrame;
	static Image[] mario_images = null;
	
	Mario(Model m)
	{
		super(m);
		prev_x = 0;
		prev_y = 0;
		groundFrames = 0;
		w = 60;
		h = 95;
		vvel = 0;
		type = "Mario";

		if(mario_images == null)
		{
			mario_images = new Image[5];
			try
			{
				mario_images[0] = ImageIO.read(new File("mario1.png"));
				mario_images[1] = ImageIO.read(new File("mario2.png"));
				mario_images[2] = ImageIO.read(new File("mario3.png"));
				mario_images[3] = ImageIO.read(new File("mario4.png"));
				mario_images[4] = ImageIO.read(new File("mario5.png"));
			} 
			catch(Exception e) 
			{
				e.printStackTrace(System.err);
				System.exit(1);
			}
		}
	}

	Mario(Json ob, Model m)
	{
		 super(m);
		 type = (String)ob.getString("type");
         x = (int)ob.getLong("x");
		 y = (int)ob.getLong("y");
		 w = (int)ob.getLong("w");
		 h = (int)ob.getLong("h");
		 vvel = (double)ob.getDouble("vvel");
	}

	void draw(Graphics g)
	{
		int MarioFrame = (Math.abs(x) / 15) % 5;
		g.drawImage(mario_images[MarioFrame], x - model.scrollPos, y, null);
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

	void remember_pos()
	{
		prev_x = x;
		prev_y = y;
	}

	boolean doesCollide(Sprite that)
	{
		if (that.x + that.w <= this.x)
			return false;
		if (that.x >= this.x + this.w)
			return false;
		if (that.y + that.h <= this.y)
			return false;
		if (that.y >= this.y + this.h)
			return false;
		return true;
	}

	void update()
	{
		model.scrollPos = x - 100;
		vvel += 3;
		y += vvel;

		if(y >= 500)
		{
			y = 500;
			vvel = 0;
		}

		if(vvel == 0)
		{
			groundFrames = 0;
		}

		else
		{
			groundFrames ++;
		}
	}
}
