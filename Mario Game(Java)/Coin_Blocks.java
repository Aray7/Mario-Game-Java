import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;

public class Coin_Blocks extends Sprite
{
	static Image[] block_images = null;
	int coins_reamin;

	Coin_Blocks(int xx, int yy, Model m)
	{
		super(m);
		prev_x = 0;
		prev_y = 0;
		type = "Coin_Blocks";
		x = xx;
		y = yy;
		w = 100;
		h = 100;
		vvel = 0;
		groundFrames = 0;
		coins_reamin = 5;

		if(block_images == null)
		{
			block_images = new Image[2];
			try
			{
					block_images[0] = ImageIO.read(new File("block1.png"));
					block_images[1] = ImageIO.read(new File("block2.png"));
			} catch(Exception e) {
				e.printStackTrace(System.err);
				System.exit(1);
			}
		}
	}

	Coin_Blocks(Json ob, Model m)
	{
		super(m);
		type = (String)ob.getString("type");
        x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
		w = (int)ob.getLong("w");
		h = (int)ob.getLong("h");
		vvel = (double)ob.getDouble("vvel");
		m.addCoin_Blocks(x, y);

		if(block_images == null)
		{
			block_images = new Image[2];
			try
			{
					block_images[0] = ImageIO.read(new File("block1.png"));
					block_images[1] = ImageIO.read(new File("block2.png"));
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

	void push_out(Sprite that)
	{
		if(that.x + that.w >= this.x && that.prev_x + that.w <= this.x) //left
		{
			that.x = this.x - that.w;
		}

		else if( that.x <= this.x + this.w && that.prev_x >= this.x + this.w) //right
		{
			that.x = this.x + this.w;
		}
		
		else if(that.y + that.h >= this.y && that.prev_y + that.h <= this.y) //above
		{
			that.vvel = 0;
			that.y = this.y - that.h;
			that.groundFrames = 0;
		}
		
		else if(that.y <= this.y + this.h && that.prev_y >= this.y + that.h) //bottom
		{
			that.y = this.y + this.h -26;
			that.vvel = 1;
			coins_reamin -= 1;
			if(coins_reamin >= 0)
				model.addCoin(x, y);
		}
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

	void draw(Graphics g)
	{
		if (coins_reamin == 0)
		{
			g.drawImage(block_images[1], x - model.scrollPos, y, null);
		} 
		else 
		{
			g.drawImage(block_images[0], x - model.scrollPos, y, null);
		}
	}

	void remember_pos()
	{
	}

	void update()
	{
		for(int i = 0; i < model.sprites.size(); i++)
		{
			Sprite s = model.sprites.get(i);
			if(s.type == "Mario")
			{
				if(doesCollide(s))
					push_out(s);
			}
			if(s.type == "Coin")
			{
			}
		}

		if(coins_reamin <= 0)
			coins_reamin = 0;
	}
}
