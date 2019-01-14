import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;
import java.util.Iterator;

class View extends JPanel
{
	Model model;
	Mario mario;
	BufferedImage background;

	View(Controller c,Model m)
	{
		model = m;
		try
		{
			background = ImageIO.read(new File("background.png"));
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}

	public void paintComponent(Graphics g)
	{
		//Clear screen
		g.setColor(new Color(128, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		//Draw background
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);

		//Set grass color and draw ground
		 g.setColor(new Color(15, 233, 64));
		 g.fillRect(0, 595, this.getWidth(), this.getHeight());

		Iterator<Sprite> it = model.sprites.iterator();
		while(it.hasNext())
		{
			Sprite s = it.next();
			s.draw(g);
		}
	}
}
