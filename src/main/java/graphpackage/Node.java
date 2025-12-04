package graphpackage;

import datapackage.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class Node extends DrawableElement{

    private int personId;

    public Node( int personId, int x, int y, int radiusPhoto, int BorderWidth)
    {
        super(x,radiusPhoto,y,BorderWidth);
        this.personId = personId;
    }

    public int getX()
    {
        return this.getX1();
    }

    public int getY()
    {
        return this.getY1();
    }

    public int getRadiusPhoto()
    {
        return this.getX2();
    }

    public int getBorderWidth()
    {
        return this.getY2();
    }

    private BufferedImage getImage()
    {
        return Model.getPerson(personId).getImage();
    }

    private String getName()
    {
        return Model.getPerson(personId).getName();
    }

    @Override
    public void onDraw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int radius = getRadiusPhoto();
        int centerX = getX();
        int centerY = getY();

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);

        BufferedImage img = getImage();
        if (img != null) {
            g2d.setClip(new java.awt.geom.Ellipse2D.Float(centerX - radius, centerY - radius, 2 * radius, 2 * radius));
            g2d.drawImage(img, centerX - radius, centerY - radius, 2 * radius, 2 * radius, null);
            g2d.setClip(null);
        }

        String name = getName();
        int rectWidth = getBorderWidth();
        int rectHeight = getBorderWidth()/8;
        int rectX = centerX - rectWidth / 2;
        int rectY = centerY + radius - rectHeight / 2;

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRoundRect(rectX, rectY, rectWidth, rectHeight, rectHeight / 2, rectHeight / 2);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRoundRect(rectX, rectY, rectWidth, rectHeight, rectHeight / 2, rectHeight / 2);

        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(name);
        int textHeight = fm.getAscent();
        int textX = rectX + (rectWidth - textWidth) / 2;
        int textY = rectY + (rectHeight + textHeight) / 2 - 2;
        g2d.drawString(name, textX, textY);
    }

    @Override
    public void onClick() {
        Color BG_COLOR = new Color(0x2B2C30);
        Color ACCENT_COLOR = new Color(100, 200, 255);
        Color TEXT_PRIMARY = new Color(255, 255, 255);
        Color TEXT_SECONDARY = new Color(180, 180, 180);

        JDialog dialog = new JDialog();
        dialog.setLayout(new BorderLayout(0, 0));
        dialog.setSize(550, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("Profile");
        dialog.setBackground(BG_COLOR);

        // Get person data
        BufferedImage personImage = Model.getPerson(personId).getImage();
        String personName = Model.getPerson(personId).getName();
        String personNotes = Model.getPerson(personId).getNotes();

        // TOP: Name Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        headerPanel.setBackground(BG_COLOR);

        JLabel nameHeaderLabel = new JLabel(personName);
        nameHeaderLabel.setFont(new Font("Dialog", Font.BOLD, 32));
        nameHeaderLabel.setForeground(ACCENT_COLOR);
        headerPanel.add(nameHeaderLabel);

        // CENTER: Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(BG_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));

        // LEFT: Image Display Panel
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(200, 200));
        imagePanel.setBackground(new Color(0x3E3F44));
        imagePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x4A4B50), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel imageLabel = new JLabel("No Photo", SwingConstants.CENTER);
        imageLabel.setForeground(TEXT_SECONDARY);
        imageLabel.setFont(new Font("Dialog", Font.ITALIC, 14));

        if (personImage != null) {
            Image scaled = personImage.getScaledInstance(180, 180, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
            imageLabel.setText(null);
        }

        imagePanel.add(imageLabel, BorderLayout.CENTER);

        // RIGHT: Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(BG_COLOR);

        // Notes Section
        JLabel notesHeaderLabel = new JLabel("Notes");
        notesHeaderLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        notesHeaderLabel.setForeground(TEXT_PRIMARY);
        notesHeaderLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea notesTextArea = new JTextArea(personNotes == null || personNotes.isEmpty() ? "No notes available" : personNotes);
        notesTextArea.setFont(new Font("Dialog", Font.PLAIN, 14));
        notesTextArea.setForeground(TEXT_SECONDARY);
        notesTextArea.setBackground(new Color(0x3E3F44));
        notesTextArea.setLineWrap(true);
        notesTextArea.setWrapStyleWord(true);
        notesTextArea.setEditable(false);
        notesTextArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x4A4B50), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        notesTextArea.setAlignmentX(Component.LEFT_ALIGNMENT);

        JScrollPane notesScrollPane = new JScrollPane(notesTextArea);
        notesScrollPane.setPreferredSize(new Dimension(250, 150));
        notesScrollPane.setBorder(null);
        notesScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(notesHeaderLabel);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(notesScrollPane);

        contentPanel.add(imagePanel, BorderLayout.WEST);
        contentPanel.add(infoPanel, BorderLayout.CENTER);

        // BOTTOM: Close Button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        bottomPanel.setBackground(BG_COLOR);

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Dialog", Font.BOLD, 14));
        closeButton.setBackground(new Color(0x3E3F44));
        closeButton.setForeground(TEXT_PRIMARY);
        closeButton.setFocusPainted(false);
        closeButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x4A4B50), 1),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        closeButton.addActionListener(e -> dialog.dispose());

        // Hover effect
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeButton.setBackground(new Color(0x4A4B50));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeButton.setBackground(new Color(0x3E3F44));
            }
        });

        bottomPanel.add(closeButton);

        // Assemble dialog
        dialog.add(headerPanel, BorderLayout.NORTH);
        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.add(bottomPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}
