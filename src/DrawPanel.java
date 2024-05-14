import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Font;

class DrawPanel extends JPanel implements MouseListener {

    private ArrayList<Card> hand;
    private Rectangle button;
    private ArrayList<Card> deck;
    private int gameStatus;

    public DrawPanel() {
        button = new Rectangle(147, 230, 160, 26);
        this.addMouseListener(this);
        deck = Card.buildDeck();
        hand = Card.buildHand(deck);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 125;
        int y = 10;
        int cardTracker = 0;
        for (int i = 0; i < 3; i++) {
            int height = 0;
            for(int a = 0; a < 3; a++) {
                Card c = hand.get(cardTracker);
                cardTracker++;
                height = c.getImage().getHeight();
                if (c.getHighlight()) {
                    g.drawRect(x, y, c.getImage().getWidth(), c.getImage().getHeight());
                }
                c.setRectangleLocation(x, y);
                g.drawImage(c.getImage(), x, y, null);
                x = x + c.getImage().getWidth() + 10;
            }
            y += height;
            x = 125;
        }
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("Check", 200, 250);
        g.drawRect((int)button.getX(), (int)button.getY(), (int)button.getWidth(), (int)button.getHeight());
        g.drawString("Cards left: " + (deck.size() + hand.size()), 150, 300);
        updateGameStatus();
        if(gameStatus == 2){
            g.drawString("You Win!", 150, 350);
        }else if(gameStatus == 1){
            g.drawString("You Lose...", 150, 350);
        }
    }

    public void mousePressed(MouseEvent e) {

        Point clicked = e.getPoint();

        if (e.getButton() == 1) {
            int pointSum = 0;
            ArrayList<Integer> positions = new ArrayList<Integer>();
            if (button.contains(clicked)) {
                for (int i = 0; i < hand.size(); i++) {
                    Card card = hand.get(i);
                    if(card.getHighlight()){
                        pointSum += card.getPointValue();
                        positions.add(i);
                    }
                }
            }
            if(pointSum == 11 || pointSum == -111){
                for(int i : positions){
                    //code for replacing a card
                    Card card = hand.get(i);
                    if(deck.size() != 0) {
                        hand.set(i, deck.remove((int) (Math.random() * deck.size())));
                    }else{
                        card.setShow(false);
                    }
                }
            }else{
                for(int i : positions){
                    //code for replacing a card
                    Card card = hand.get(i);
                    card.setHighlight(false);
                }
            }

//            for (int i = 0; i < hand.size(); i++) {
//                Rectangle box = hand.get(i).getCardBox();
//                if (box.contains(clicked)) {
//                    hand.get(i).flipCard();
//                }
//            }
        }

        if (e.getButton() == 3) {
            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    Card card = hand.get(i);
                    card.flipHighlight();
                }
            }
        }


    }

    public void updateGameStatus(){
        if(deck.size() == 0 && hand.size() == 0){
            gameStatus = 2;
            //win!
        }else if(!validMovePossible()){
            gameStatus = 1;
            //lose
        }else{
            gameStatus = 0;
            //ongoing
        }
    }

    public boolean validMovePossible(){
        boolean jP,qP,kP;
        kP = false;
        jP = false;
        qP = false;
        for(int i = 0; i < hand.size(); i++){
            for(int a = 0; a < hand.size(); a++){
                if(hand.get(i).getPointValue() + hand.get(a).getPointValue() == 11){
                    return true;
                }
                if(hand.get(i).getPointValue() == -100){
                    qP = true;
                }
                if(hand.get(i).getPointValue() == -10){
                    kP = true;
                }
                if(hand.get(i).getPointValue() == -1){
                    jP = true;
                }
            }
        }
        if(qP && kP && jP){
            return true;
        }
        return false;
    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}