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
        g.drawString("Check (NYI)", 150, 250);
        g.drawRect((int)button.getX(), (int)button.getY(), (int)button.getWidth(), (int)button.getHeight());
    }

    public void mousePressed(MouseEvent e) {

        Point clicked = e.getPoint();

        if (e.getButton() == 1) {
            if (button.contains(clicked)) {
                //TODO: Put code here to check if all highlighted cards are valid
                hand = Card.buildHand();
            }

            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    hand.get(i).flipCard();
                }
            }
        }

        if (e.getButton() == 3) {
            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    Card card = hand.get(i);
                    if(card.getHighlight()){
                        //code for replacing a card
                        if(deck.size() != 0) {
                            hand.set(i, deck.remove((int) (Math.random() * deck.size())));
                        }else{
                            card.setShow(false);
                        }
                    }
                    card.flipHighlight();
                }
            }
        }


    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}