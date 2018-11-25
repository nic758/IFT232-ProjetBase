package chess;

import java.awt.Point;


//Utilitaires pour convertir la notation algébrique en positions et en pièces correctement typées et colorées.
public class ChessUtils {

	// Tables de conversion pour les noms des pièces vers des types et des
	// couleurs.
	public static final char typeLetters[]={'p','n','b','r','q','k'};
	public static final char colorLetters[]={'w','b'};
	public static final int typeConverts[];
	public static final int colorConverts[];

	// Types de pièces
	public static final int TYPE_NONE = -1;
	public static final int TYPE_PAWN = 0;
	public static final int TYPE_KNIGHT = 1;
	public static final int TYPE_BISHOP = 2;
	public static final int TYPE_ROOK = 3;
	public static final int TYPE_QUEEN = 4;
	public static final int TYPE_KING = 5;

	// Couleurs
	public static final int COLORLESS = -1;
	public static final int WHITE = 0;
	public static final int BLACK = 1;

	//Initialisation des tables de conversion
	static {

		colorConverts = new int[26];
		for (int i = 0; i < 26; i++) {
			colorConverts[i] = COLORLESS;
		}
		colorConverts['w' - 'a'] = WHITE;
		colorConverts['b' - 'a'] = BLACK;

		typeConverts = new int[26];
		for (int i = 0; i < 26; i++) {
			typeConverts[i] = -1;
		}

		typeConverts['p' - 'a'] = TYPE_PAWN;
		typeConverts['n' - 'a'] = TYPE_KNIGHT;
		typeConverts['b' - 'a'] = TYPE_BISHOP;
		typeConverts['r' - 'a'] = TYPE_ROOK;
		typeConverts['q' - 'a'] = TYPE_QUEEN;
		typeConverts['k' - 'a'] = TYPE_KING;
	}

	//Extrait le type de pièce de son nom
	public static int getType(String name) {
		if (name.length() != 2)
			throw new IllegalArgumentException("Invalid chess piece name: " + name);

		int type = typeConverts[name.getBytes()[1] - 'a'];

		if (type == TYPE_NONE)
			throw new IllegalArgumentException("Invalid chess piece name: " + name);
		return type;

	}

	//Extrait la couleur de la pièce de son nom
	public static int getColor(String name) {
		if (name.length() != 2)
			throw new IllegalArgumentException("Invalid chess piece type: " + name);

		int color = colorConverts[name.getBytes()[0] - 'a'];

		if (color == COLORLESS)
			throw new IllegalArgumentException("Invalid chess piece color: " + name);
		return color;

	}

	//Crée une coordonnée (0-7,0-7) à partir d'une coordonnée algébrique comme "a1" ou "g8"
	public static Point convertAlgebraicPosition(String pos) {

		if (pos.length() != 2)
			throw new IllegalArgumentException("Wrong chess coordinates: " + pos);
		if (pos.getBytes()[0] < 'a' || pos.getBytes()[0] > 'h' || pos.getBytes()[1] < '1' || pos.getBytes()[1] > '8')
			throw new IllegalArgumentException("Wrong chess coordinates: " + pos);

		return new Point(pos.getBytes()[0] - 'a', 8 - (pos.getBytes()[1] - '0'));
	}
	
	//Crée une coordonnée algébrique à partir d'une coordonnée de grille
	public static String makeAlgebraicPosition(int x,int y){
		if (x < 0 || x > 7 || y < 0 || y > 7)			
			throw new IllegalArgumentException("Wrong chess coordinates: (" + x+","+y+")");
		
		return ""+((char)(x+'a'))+(8-y);	
	}
	
	//Construit un nom de pièce à partir d'une couleur et d'un type
	public static String makePieceName(int color,int type){
		if(color>BLACK || color<WHITE ||type<TYPE_PAWN||type>TYPE_KING)
			throw new IllegalArgumentException("Wrong chess piece descriptor: (" +color+","+type+")");
		
		return ""+colorLetters[color]+typeLetters[type];
		
	}

}
