import java.util.ArrayList;

class Model
{
	Mario mario;
	ArrayList<Sprite> sprites;
	int scrollPos;

	Model()
	{
		mario = new Mario(this);
		sprites = new ArrayList<Sprite>();
		sprites.add(mario);
	}

	public void update()
	{
		for(int i = 0; i < sprites.size(); i++)
		{
			Sprite s = sprites.get(i);
			s.update();
			
			if(s.type.equals("Coins"))
			{
				if(s.y > 500)
				{
					sprites.remove(s);
					i--;
				}
			}
		}
	}

	void addBrick(int x, int y, int w, int h)
	{
		Sprite s = new Brick(x, y, w, h, this);
		sprites.add(s);
	}

	void addCoin_Blocks(int x, int y)
	{
		Sprite s = new Coin_Blocks(x, y, this);
		sprites.add(s);
	}

	void addCoin(int x, int y)
	{
		Sprite s = new Coin(x, y, this);
		sprites.add(s);
		
		
	}
	
	void unmarshal(Json ob)
	{
		 Sprite mario2 = mario;
         sprites.clear();
         sprites.add(mario2);
		 Json json_sprites = ob.get("sprites");
		 for (int i = 0; i < json_sprites.size(); i++)
		 {
			 Json j = json_sprites.get(i);
			 String str = j.getString("type");
			 if(str.equals("Mario"))
			 {
			 }
			 
			 if(str.equals("Brick"))
			 {
			 	Brick b = new Brick(j, this);
			 }
			 
			 if(str.equals("Coin_Blocks"))
			 {
			 	Coin_Blocks cB = new Coin_Blocks(j, this);
			 }

			 if(str.equals("Coin"))
			 {
			 	Coin c = new Coin(j, this);
			 }
		 }
	}
	
	Json marshal()
    {	
         Json ob = Json.newObject();
		 Json json_sprites = Json.newList();
		 ob.add("sprites", json_sprites);
		 for(int i = 0; i < sprites.size(); i++)
		 {
			 Sprite s = sprites.get(i);
			 Json j = s.marshall();
			 json_sprites.add(j);
		 }
         return ob;
    }
	
	void save(String filename)
	{
		Json ob = marshal();
		ob.save(filename);
	}
	
	void load(String filename)
	{
		Json ob = Json.load(filename);
		unmarshal(ob);
	}
}