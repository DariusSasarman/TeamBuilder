package graphpackage;

import datapackage.Bond;
import datapackage.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class Edge extends DrawableElement{

    private int bondId;

    public Edge(int bondId, int x1, int x2, int y1, int y2)
    {
        super(x1,x2,y1,y2);
        this.bondId = bondId;
    }

    @Override
    public void onDraw(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        int rating = Model.getBond(bondId).getRating();

        float width = 30/ Math.max(1f, 5f * rating / 10f) ;
        g2d.setStroke(new BasicStroke(width));

        Color color;
        if (rating <= 5) {
            // red to yellow
            int green = (int)(255 * rating / 5.0);
            color = new Color(255, green, 0);
        } else {
            // yellow to green
            int red = (int)(255 * (10 - rating) / 5.0);
            color = new Color(red, 255, 0);
        }
        g2d.setColor(color);
        g2d.drawLine(this.getX1(), this.getY1(), this.getX2(), this.getY2());
    }

    public int getBondId() {
        return bondId;
    }

    public void setBondId(int bondId) {
        this.bondId = bondId;
    }

    @Override
    public void onClick() {
        Color BG_COLOR = new Color(0x2B2C30);
        Color ACCENT_COLOR = new Color(100, 200, 255);
        Color TEXT_PRIMARY = new Color(255, 255, 255);
        Color TEXT_SECONDARY = new Color(180, 180, 180);
        Color CARD_BG = new Color(0x3E3F44);

        JDialog dialog = new JDialog();
        dialog.setLayout(new BorderLayout(0, 0));
        dialog.setSize(900, 550);
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("Relationship Details");
        dialog.setBackground(BG_COLOR);

        // Get bond data
        int headId = Model.getBond(bondId).getHeadId();
        int tailId = Model.getBond(bondId).getTailId();
        int currentRating = Model.getBond(bondId).getRating();
        String currentNotes = Model.getBond(bondId).getNotes();
        String person1Name = Model.getPerson(headId).getName();
        String person2Name = Model.getPerson(tailId).getName();
        BufferedImage person1Image = Model.getPerson(headId).getImage();
        BufferedImage person2Image = Model.getPerson(tailId).getImage();

        // TOP: Display the two people with images in horizontal layout
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 30));
        topPanel.setBackground(BG_COLOR);

        Font nameFont = new Font("Dialog", Font.BOLD, 28);
        Font connectorFont = new Font("Dialog", Font.PLAIN, 36);

        // Person 1 Image
        JLabel person1ImageLabel = new JLabel("", SwingConstants.CENTER);
        person1ImageLabel.setPreferredSize(new Dimension(100, 100));
        person1ImageLabel.setBackground(CARD_BG);
        person1ImageLabel.setOpaque(true);
        person1ImageLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x4A4B50), 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        if (person1Image != null) {
            Image scaled = person1Image.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            person1ImageLabel.setIcon(new ImageIcon(scaled));
        } else {
            person1ImageLabel.setText("No Photo");
            person1ImageLabel.setForeground(TEXT_SECONDARY);
            person1ImageLabel.setFont(new Font("Dialog", Font.ITALIC, 12));
        }

        // Person 1 Name
        JLabel person1NameLabel = new JLabel(person1Name);
        person1NameLabel.setFont(nameFont);
        person1NameLabel.setForeground(TEXT_PRIMARY);

        // Visual connector
        JLabel connectorLabel = new JLabel("⟷");
        connectorLabel.setFont(connectorFont);
        connectorLabel.setForeground(ACCENT_COLOR);

        // Person 2 Name
        JLabel person2NameLabel = new JLabel(person2Name);
        person2NameLabel.setFont(nameFont);
        person2NameLabel.setForeground(TEXT_PRIMARY);

        // Person 2 Image
        JLabel person2ImageLabel = new JLabel("", SwingConstants.CENTER);
        person2ImageLabel.setPreferredSize(new Dimension(100, 100));
        person2ImageLabel.setBackground(CARD_BG);
        person2ImageLabel.setOpaque(true);
        person2ImageLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x4A4B50), 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        if (person2Image != null) {
            Image scaled = person2Image.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            person2ImageLabel.setIcon(new ImageIcon(scaled));
        } else {
            person2ImageLabel.setText("No Photo");
            person2ImageLabel.setForeground(TEXT_SECONDARY);
            person2ImageLabel.setFont(new Font("Dialog", Font.ITALIC, 12));
        }

        topPanel.add(person1ImageLabel);
        topPanel.add(person1NameLabel);
        topPanel.add(connectorLabel);
        topPanel.add(person2NameLabel);
        topPanel.add(person2ImageLabel);

        // CENTER: Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(BG_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        // Bond Strength Section
        JPanel strengthPanel = new JPanel(new BorderLayout(15, 10));
        strengthPanel.setBackground(CARD_BG);
        strengthPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x4A4B50), 2),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        strengthPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        JLabel strengthHeaderLabel = new JLabel("Bond Strength");
        strengthHeaderLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        strengthHeaderLabel.setForeground(TEXT_SECONDARY);

        // Visual rating display
        JPanel ratingDisplayPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        ratingDisplayPanel.setBackground(CARD_BG);

        JLabel ratingNumberLabel = new JLabel(currentRating + "/10");
        ratingNumberLabel.setFont(new Font("Dialog", Font.BOLD, 32));

        // Color-code the rating
        Color ratingColor;
        if (currentRating >= 8) {
            ratingColor = new Color(100, 255, 100);
        } else if (currentRating >= 5) {
            ratingColor = new Color(255, 200, 50);
        } else {
            ratingColor = new Color(255, 100, 100);
        }
        ratingNumberLabel.setForeground(ratingColor);

        // Visual bar representation
        JPanel ratingBarContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        ratingBarContainer.setBackground(CARD_BG);
        for (int i = 1; i <= 10; i++) {
            JLabel dot = new JLabel("●");
            dot.setFont(new Font("Dialog", Font.PLAIN, 20));
            if (i <= currentRating) {
                dot.setForeground(ratingColor);
            } else {
                dot.setForeground(new Color(0x4A4B50));
            }
            ratingBarContainer.add(dot);
        }

        ratingDisplayPanel.add(ratingNumberLabel);
        ratingDisplayPanel.add(Box.createHorizontalStrut(15));
        ratingDisplayPanel.add(ratingBarContainer);

        JPanel strengthContentPanel = new JPanel();
        strengthContentPanel.setLayout(new BoxLayout(strengthContentPanel, BoxLayout.Y_AXIS));
        strengthContentPanel.setBackground(CARD_BG);
        strengthContentPanel.add(strengthHeaderLabel);
        strengthContentPanel.add(Box.createVerticalStrut(5));
        strengthContentPanel.add(ratingDisplayPanel);

        strengthPanel.add(strengthContentPanel, BorderLayout.CENTER);

        // Notes Section
        JPanel notesPanel = new JPanel(new BorderLayout(10, 10));
        notesPanel.setBackground(CARD_BG);
        notesPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x4A4B50), 2),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JLabel notesHeaderLabel = new JLabel("Notes");
        notesHeaderLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        notesHeaderLabel.setForeground(TEXT_SECONDARY);

        JTextArea notesTextArea = new JTextArea(currentNotes == null || currentNotes.isEmpty() ? "No notes recorded" : currentNotes);
        notesTextArea.setFont(new Font("Dialog", Font.PLAIN, 14));
        notesTextArea.setForeground(TEXT_PRIMARY);
        notesTextArea.setBackground(new Color(0x35363A));
        notesTextArea.setLineWrap(true);
        notesTextArea.setWrapStyleWord(true);
        notesTextArea.setEditable(false);
        notesTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        notesTextArea.setRows(3);

        JScrollPane notesScrollPane = new JScrollPane(notesTextArea);
        notesScrollPane.setBorder(BorderFactory.createLineBorder(new Color(0x4A4B50), 1));
        notesScrollPane.setBackground(new Color(0x35363A));

        notesPanel.add(notesHeaderLabel, BorderLayout.NORTH);
        notesPanel.add(notesScrollPane, BorderLayout.CENTER);

        contentPanel.add(strengthPanel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(notesPanel);

        // BOTTOM: Close Button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        bottomPanel.setBackground(BG_COLOR);

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Dialog", Font.BOLD, 14));
        closeButton.setBackground(CARD_BG);
        closeButton.setForeground(TEXT_PRIMARY);
        closeButton.setFocusPainted(false);
        closeButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x4A4B50), 1),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        closeButton.addActionListener(e -> dialog.dispose());

        // Hover effect
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeButton.setBackground(new Color(0x4A4B50));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeButton.setBackground(CARD_BG);
            }
        });

        bottomPanel.add(closeButton);

        // Assemble dialog
        dialog.add(topPanel, BorderLayout.NORTH);
        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.add(bottomPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}
