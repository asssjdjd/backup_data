package user_view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import dao.UserDAO;
import model.User;


public class SellerHomeFrm extends JFrame implements ActionListener{
	private JButton btnBookTicket;
    private JLabel lblUsername;
    private User gobalUser;

    public SellerHomeFrm(User user) {
        super("Trang đặt vé");
//        gắn user làm biến toàn cục 
        gobalUser = user;

        // Giao diện chính
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null); // sử dụng layout tự do để dễ canh vị trí

        // Nhãn Tên đăng nhập ở góc trên bên phải
        // thay bang ten dang nhap cua nguoi dung : 
        String username = user.getUsername();
        lblUsername = new JLabel("" + username);
        lblUsername.setBounds(300, 10, 100, 20); // điều chỉnh vị trí theo yêu cầu
        mainPanel.add(lblUsername);

        // Nhãn "Trang đặt vé"
        JLabel lblTitle = new JLabel("Trang đặt vé");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(100, 50, 200, 40); // căn giữa trên khung
        mainPanel.add(lblTitle);

        // Nút "Đặt vé"
        btnBookTicket = new JButton("Đặt vé");
        btnBookTicket.setBounds(140, 120, 120, 40); // căn giữa dưới nhãn
        btnBookTicket.setFocusPainted(false);
        btnBookTicket.addActionListener(this);
        mainPanel.add(btnBookTicket);

        // Cấu hình JFrame
        this.setContentPane(mainPanel);
        this.setSize(400, 250);
        this.setLocationRelativeTo(null); // căn giữa màn hình
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBookTicket) {
        	(new SearchTourFrm(gobalUser)).setVisible(true);
        	this.dispose();
//            JOptionPane.showMessageDialog(this, "Bạn đã đặt vé thành công!");
        }
    }

  
}
