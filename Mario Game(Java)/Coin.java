import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.Random;

public class Coin extends Sprite
{
	static Image[] coin_images = null;
	double vvel;
    double horizontal;
    double gravity;
    Random random = new Random();

	Coin(int xx, int yy, Model m)
	{
		super(m);
		prev_x = 0;
		prev_y = 0;
		type = "Coins";
		x = xx;
		y = yy;
		w = 60;
		h = 60;
		vvel = 0;
		groundFrames = 0;
		vvel = -random.nextInt(60)-20;
        horizontal = random.nextInt(30)-15;

		if(coin_images == null)
		{
			coin_images = new Image[5];
			try
			{
				coin_images[0] = ImageIO.read(new File("coin.png"));
				coin_images[1] = ImageIO.read(new File("coin.png"));
				coin_images[2] = ImageIO.read(new File("coin.png"));
				coin_images[3] = ImageIO.read(new File("coin.png"));
				coin_images[4] = ImageIO.read(new File("coin.png"));
			} 
			catch(Exception e) 
			{
				e.printStackTrace(System.err);
				System.exit(1);
			}
		}
	}

	Coin(Json ob, Model m)
	{
		super(m);
		type = (String)ob.getString("type");
        x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
		w = (int)ob.getLong("w");
		h = (int)ob.getLong("h");
		vvel = (double)ob.getDouble("vvel");
		m.addCoin(x, y);

		if(coin_images == null)
		{
			coin_images = new Image[5];
			try
			{
				coin_images[0] = ImageIO.read(new File("coin.png"));
				coin_images[1] = ImageIO.read(new File("coin.png"));
				coin_images[2] = ImageIO.read(new File("coin.png"));
				coin_images[3] = ImageIO.read(new File("coin.png"));
				coin_images[4] = ImageIO.read(new File("coin.png"));
			} 
			catch(Exception e) 
			{
				e.printStackTrace(System.err);
				System.exit(1);
			}
		}
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

	void draw(Graphics g)
	{
		g.drawImage(coin_images[0], x - model.scrollPos, y, null);
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

	void update()
	{
		gravity = gravity + 2.54;
        vvel = vvel + gravity;
        y += vvel;
        x += horizontal;
        
       
	}
}
