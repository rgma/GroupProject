public class Player {
	int x;
	int y;
	int xTile;
	int yTile;

	public Player(){
		x = 0;
		y = 0;
		xTile = 0;
		yTile = 0;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getXTile(){
		return xTile;
	}

	public int getYTile(){
		return yTile;
	}
	public void changeX(int x){
		this.x = this.x + x;
		this.xTile = this.xTile + (x/32);
	}
	
	public void changeY(int y){
		this.y = this.y + y;
		this.yTile = this.yTile + (y/32);
	}
}
