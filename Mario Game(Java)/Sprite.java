import java.awt.Graphics;

abstract class Sprite extends Object
{
	Model model;
	int x, y, w, h, groundFrames, prev_x, prev_y;
	double vvel;
	String type;

	abstract Json marshall();
	abstract void draw(Graphics g);
	abstract void update();
	abstract void remember_pos();
	abstract boolean doesCollide(Sprite s);


	Sprite(Model  m)
	{
		model = m;
	}
}
