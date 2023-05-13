package Main;

import entity.Entity;

public class CollisionChecker {
	
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	public void checkTile(Entity entity) {
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
	
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize - 2;
		int entityBottomRow = entityBottomWorldY/gp.tileSize - 2;
		
		
		int tileNum1, tileNum2;
		switch(entity.direction) {
		case "left":
			if(entity.action == "jump") {
				entity.collisionOn = false;
				entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize - 2;
				if(entityTopRow >= 0) {
					tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityLeftCol];
					tileNum2 = gp.tileM.mapTileNum[entityTopRow][entityRightCol];
					if((tileNum1 != 0 && gp.tileM.tile[tileNum1].collision) || (tileNum2 != 0 && gp.tileM.tile[tileNum2].collision)) {
						entity.jumpStrength = 0;
						break;
					}
				}
			}
			
			//bottom
			entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
			
			if(entityLeftCol >= 0 && entityBottomRow >= 0) {
				tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityLeftCol];
				if(tileNum2 != 0 && gp.tileM.tile[tileNum2].collision == true) {
					entity.collisionOn = true;
				}
			}else
				entity.collisionOn = true;
			break;
			
		case "right":
			if(entity.action == "jump") {
				entity.collisionOn = false;
				entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize - 2;
				if(entityTopRow >= 0) {
					tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityLeftCol];
					tileNum2 = gp.tileM.mapTileNum[entityTopRow][entityRightCol];
					if((tileNum1 != 0 && gp.tileM.tile[tileNum1].collision) || (tileNum2 != 0 && gp.tileM.tile[tileNum2].collision)) {
						entity.jumpStrength = 0;
						break;
					}
				}
			}
			
			//bottom
			entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
			
			if(entityRightCol < gp.maxWorldCol && entityBottomRow >= 0) {
				tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityRightCol];

				if(tileNum2 != 0 && gp.tileM.tile[tileNum2].collision == true) {
					entity.collisionOn = true;
				}
			}else 
				entity.collisionOn = true;
			break;
		}
	}

	public void checkFloor(Entity entity) {
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;

		int entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		
		if(entityLeftCol >= 0 && entityRightCol >=0) {
			int tileNum1 = gp.tileM.mapTileNum[entityBottomRow-1][entityLeftCol];
			int tileNum2 = gp.tileM.mapTileNum[entityBottomRow-1][entityRightCol];
			
			if((tileNum1 != 0 && gp.tileM.tile[tileNum1].collision) || (tileNum2 != 0 && gp.tileM.tile[tileNum2].collision)) {
				entity.floorHeight = (entityBottomRow-1) * gp.tileSize;
			}else {
				entity.floorHeight = entityBottomRow * gp.tileSize;
			}
		}
	}
}
