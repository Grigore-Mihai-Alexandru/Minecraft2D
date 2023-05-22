package Main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class MapGenerator {
	GamePanel gp;
	private int[][] world;
	Random random = new Random();
	
	private final int BLOCK_AIR = 0;
    private final int BLOCK_GRASS = 1;
    private final int BLOCK_STONE = 2;
    private final int BLOCK_COAL_ORE = 3;
    private final int BLOCK_IRON_ORE = 4;
	
	public MapGenerator(GamePanel gp){
		this.gp = gp;
		
	}
	
	public int[][] generateWorld() {
        int[][] map = new int[gp.maxWorldCol][gp.maxWorldRow];
        Random random = new Random();

        for (int x = 0; x < gp.maxWorldCol; x++) {
            for (int y = 0; y < gp.maxWorldRow; y++) {
                // Generate block type based on coordinates
                if (y == gp.maxWorldRow - 1) {
                    map[x][y] = BLOCK_GRASS; // Top row is always grass
                } else if (y >= gp.maxWorldRow - 3) {
                    map[x][y] = random.nextInt(2) + 3; // Randomly choose between coal ore (3) or iron ore (4)
                } else {
                    map[x][y] = random.nextInt(2) + 1; // Randomly choose between grass (1) or stone (2)
                }
            }
        }

        return map;
    }

//    private static final int WORLD_SIZE = 128;
//    private static final int SEA_LEVEL = 64;
//
//    private static final double CAVE_FREQUENCY = 0.01;
//    private static final int CAVE_HEIGHT = 40;
//
//    private static final int[] BIOME_FREQUENCY = { 20, 10, 5 }; // Plains, Forest, Desert
//    private static final int BIOME_THRESHOLD = 35;
//
//    private Random random;
//
//    public MapGenerator() {
//        random = new Random();
//        world = new int[WORLD_SIZE][WORLD_SIZE];
//    }
//
//    public void generateWorld() {
//        generateHeightMap();
//        generateBiomes();
//        generateCaves();
//    }
//
//    private void generateHeightMap() {
//        for (int x = 0; x < WORLD_SIZE; x++) {
//            int height = (int) (SEA_LEVEL + Math.sin(x / 10.0) * 10);
//
//            for (int y = 0; y < WORLD_SIZE; y++) {
//                if (y < height) {
//                    world[x][y] = 1; // Stone layer
//                } else if (y < SEA_LEVEL) {
//                    world[x][y] = 2; // Water layer
//                } else {
//                    world[x][y] = 0; // Air layer
//                }
//            }
//        }
//    }
//
//    private void generateBiomes() {
//        for (int x = 0; x < WORLD_SIZE; x++) {
//            for (int y = 0; y < WORLD_SIZE; y++) {
//                if (y >= SEA_LEVEL) {
//                    int biome = random.nextInt(BIOME_THRESHOLD);
//
//                    if (biome < BIOME_FREQUENCY[0]) {
//                        world[x][y] = 3; // Plains
//                    } else if (biome < BIOME_THRESHOLD - BIOME_FREQUENCY[2]) {
//                        world[x][y] = 4; // Forest
//                    } else {
//                        world[x][y] = 5; // Desert
//                    }
//                }
//            }
//        }
//    }
//
//    private void generateCaves() {
//        for (int x = 0; x < WORLD_SIZE; x++) {
//            for (int y = SEA_LEVEL; y < WORLD_SIZE; y++) {
//                if (random.nextDouble() < CAVE_FREQUENCY) {
//                    int caveHeight = (int) (CAVE_HEIGHT * random.nextDouble());
//
//                    for (int caveY = y; caveY > y - caveHeight; caveY--) {
//                        if (caveY >= SEA_LEVEL && caveY < WORLD_SIZE) {
//                            world[x][caveY] = 0; // Air layer
//                        }
//                    }
//                }
//            }
//        }
//    }
//
    public void saveWorldToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (int y = 0; y < gp.maxWorldRow; y++) {
                for (int x = 0; x < gp.maxWorldCol; x++) {
                    writer.print(world[x][y] + " ");
                }
                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
