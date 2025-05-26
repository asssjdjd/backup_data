package user_view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import model.Client;
import model.Tour;

import dao.BillDAO;


public class ConfirmBillFrm extends JFrame implements ActionListener {
	private JButton btnConfirm, btnCancel;
	private Tour tour;
	private Client client;

	public ConfirmBillFrm(Tour tour, Client client) {
		super("Xác nhận thông tin đặt tour");

		this.tour = tour;
		this.client = client;

		this.setSize(600, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel lblTitle = new JLabel("XÁC NHẬN THÔNG TIN ĐẶT TOUR");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(lblTitle);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// Thông tin khách hàng
		mainPanel.add(createInfoLabel("Mã khách hàng: " + client.getId()));
		mainPanel.add(createInfoLabel("Tên khách hàng: " + client.getName()));
		mainPanel.add(createInfoLabel("Email: " + client.getEmail()));

		mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

		// Thông tin tour
		mainPanel.add(createInfoLabel("Tên tour: " + tour.getNameTour()));
		mainPanel.add(createInfoLabel("Điểm khởi hành: " + tour.getDepaturePoint()));
		mainPanel.add(createInfoLabel("Điểm đến: " + tour.getDestination()));
		mainPanel.add(createInfoLabel("Số lượng tối đa: " + tour.getMaxCapacity()));
		mainPanel.add(createInfoLabel("Giảm giá: " + tour.getSaleOff() + "%"));
		mainPanel.add(createInfoLabel("Đã đặt: " + tour.getBookedPeople() + " người"));
		mainPanel.add(createInfoLabel("Giá tiền: " + tour.getBookedPeople() * tour.getPrice() * (1 - tour.getSaleOff())));
		mainPanel.add(createInfoLabel("Ngày bắt đầu : " + tour.getStartDate()));
		mainPanel.add(createInfoLabel("Ngày kết thúc : " + tour.getEndDate()));
		mainPanel.add(createInfoLabel("Ghi chú: " + tour.getNote()));

		mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

		// Button panel
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
		btnConfirm = new JButton("Xác nhận");
		btnCancel = new JButton("Hủy");

		btnConfirm.setPreferredSize(new Dimension(120, 30));
		btnCancel.setPreferredSize(new Dimension(120, 30));

		btnConfirm.addActionListener(this);
		btnCancel.addActionListener(this);

		buttonPanel.add(btnConfirm);
		buttonPanel.add(btnCancel);

		mainPanel.add(buttonPanel);

		this.setContentPane(mainPanel);
	}

	private JLabel createInfoLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(new Font("Arial", Font.PLAIN, 16));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		return label;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnConfirm) {
			int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc đặt vé", "Xác nhận", JOptionPane.YES_NO_OPTION);
			BillDAO billDAO = new BillDAO();
			boolean check = billDAO.updateBill(tour, client);
			if(result == JOptionPane.YES_OPTION && check == true) {
				JOptionPane.showMessageDialog(this, "Đã xác nhận đặt tour thành công!");
			}else {
				JOptionPane.showMessageDialog(this, "Đặt vé thất bại");
			}
            this.dispose();
            new SellerHomeFrm(tour.getUser()).setVisible(true);
        } else if (e.getSource() == btnCancel) {
            int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn hủy?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                this.dispose();
                new SellerHomeFrm(tour.getUser()).setVisible(true);
            }
        }
	}
}
