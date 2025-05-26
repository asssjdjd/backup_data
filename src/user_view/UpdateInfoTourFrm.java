package user_view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import dao.TourDAO;
import model.Tour;

public class UpdateInfoTourFrm extends JFrame implements ActionListener {

	private JTextField quantityField;
	private JTextArea noteArea;
	private Tour gobalTour;

	public UpdateInfoTourFrm(Tour tour) {
		super();
		setTitle("Xác nhận thông tin");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		gobalTour = tour;

		Font titleFont = new Font("Arial", Font.BOLD, 20);
		Font labelFont = new Font("Arial", Font.PLAIN, 14);

		JPanel panel = new JPanel();
		panel.setLayout(null);

		JLabel titleLabel = new JLabel("Xác nhận thông tin");
		titleLabel.setFont(titleFont);
		titleLabel.setBounds(100, 10, 250, 30);
		panel.add(titleLabel);

		JLabel quantityLabel = new JLabel("Số lượng người thực tế");
		quantityLabel.setFont(labelFont);
		quantityLabel.setBounds(30, 60, 160, 25);
		panel.add(quantityLabel);

		quantityField = new JTextField();
		quantityField.setBounds(200, 60, 150, 30);
		panel.add(quantityField);

		JLabel noteLabel = new JLabel("Lưu ý");
		noteLabel.setFont(labelFont);
		noteLabel.setBounds(30, 110, 100, 25);
		panel.add(noteLabel);

		noteArea = new JTextArea();
		noteArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
		JScrollPane scrollPane = new JScrollPane(noteArea);
		scrollPane.setBounds(30, 140, 320, 80);
		panel.add(scrollPane);

		JButton confirmButton = new JButton("Xác nhận");
		confirmButton.setFont(labelFont);
		confirmButton.setBounds(140, 230, 120, 30);
		confirmButton.addActionListener(this);
		confirmButton.putClientProperty("tour", tour);
		panel.add(confirmButton);

		add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String quantityText = quantityField.getText().trim();
		String noteText = noteArea.getText().trim();

		// Kiểm tra dữ liệu
		if (quantityText.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng người.", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int quantity = Integer.parseInt(quantityText);
		
		JButton btn = (JButton) e.getSource();
        Tour tour = (Tour) btn.getClientProperty("tour");
        
//        update thông tin
        tour.setBookedPeople(quantity);
        tour.setNote(noteText);
        
        (new SearchClientFrm(tour)).setVisible(true);

//		String message = "Số lượng người thực tế: " + quantity + "\n" + "Ghi chú: " + noteText + " name tour :"+ tour.getNameTour();
//		JOptionPane.showMessageDialog(this, message, "Thông tin đã nhập", JOptionPane.INFORMATION_MESSAGE);
		
		this.dispose();

	}

	
}
