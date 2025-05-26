package user_view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import model.Location;
import model.Tour;
import model.User;
import dao.TourDAO;
import dao.*;

public class SearchTourFrm extends JFrame implements ActionListener {
    private JTextField txtStartPlace, txtEndPlace, txtQuantity, txtStartDate, txtEndDate;
    private JButton btnSearch, btnReset;
    private JTable table;
    private DefaultTableModel tableModel;
    private User gobalUser;

    public SearchTourFrm(User user) {
        super("Tìm kiếm Tour");
        
        gobalUser = user;

        this.setSize(900, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel("Tìm kiếm Tour");
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel inputPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        inputPanel1.add(new JLabel("Địa điểm bắt đầu"));
        txtStartPlace = new JTextField(10);
        inputPanel1.add(txtStartPlace);

        inputPanel1.add(new JLabel("Địa điểm kết thúc"));
        txtEndPlace = new JTextField(10);
        inputPanel1.add(txtEndPlace);

        inputPanel1.add(new JLabel("Số lượng"));
        txtQuantity = new JTextField(5);
        inputPanel1.add(txtQuantity);

        JPanel inputPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        inputPanel2.add(new JLabel("Ngày bắt đầu (dd/mm/YYYY)"));
        txtStartDate = new JTextField(10);
        inputPanel2.add(txtStartDate);

        inputPanel2.add(new JLabel("Ngày kết thúc (dd/mm/YYYY)"));
        txtEndDate = new JTextField(10);
        inputPanel2.add(txtEndDate);

        btnSearch = new JButton("Search");
        btnSearch.addActionListener(this);

        btnReset = new JButton("Reset");
        btnReset.addActionListener(this);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(btnReset);
        btnPanel.add(btnSearch);

        mainPanel.add(inputPanel1);
        mainPanel.add(inputPanel2);
        mainPanel.add(btnPanel);

        
        String[] columnNames = { "Stt", "Tên", "Điểm đến", "Điểm bắt đầu", "Số lượng tối đa", "Giảm giá", "Lựa chọn" };
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 6) return JPanel.class; 
                return Object.class;
            }
        };

        
        table.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));

        table.getColumnModel().getColumn(0).setPreferredWidth(40);  
        table.getColumnModel().getColumn(1).setPreferredWidth(150); 
        table.getColumnModel().getColumn(2).setPreferredWidth(150); 
        table.getColumnModel().getColumn(3).setPreferredWidth(150); 
        table.getColumnModel().getColumn(4).setPreferredWidth(80); 
        table.getColumnModel().getColumn(5).setPreferredWidth(80);  
        table.getColumnModel().getColumn(6).setPreferredWidth(160); 


        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        table.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(880, 300)); 
        mainPanel.add(scrollPane);

        this.setContentPane(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSearch) {
            tableModel.setRowCount(0);
            TourDAO tDao = new TourDAO();
            ArrayList<Tour> resultArrayList = tDao.searchFreeTour(
                    txtStartPlace.getText(),
                    txtEndPlace.getText(),
                    txtQuantity.getText(),
                    txtStartDate.getText(),
                    txtEndDate.getText(),
                    gobalUser
            );

            int index = 1;
            for (Tour itemTour : resultArrayList) {
                // Tạo panel chứa 2 nút
                JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
                panelButtons.setPreferredSize(new Dimension(150, 25));

                JButton btnDetail = new JButton("Chi tiết");
                btnDetail.setFont(new Font("Arial", Font.PLAIN, 10));
                btnDetail.setPreferredSize(new Dimension(70, 20));
                btnDetail.setActionCommand("detail-" + itemTour.getId());
                btnDetail.addActionListener(buttonActionListener);

                JButton btnSelect = new JButton("Chọn");
                btnSelect.setFont(new Font("Arial", Font.PLAIN, 10));
                btnSelect.setPreferredSize(new Dimension(70, 20));
                btnSelect.setActionCommand("select-" + itemTour.getId());
                btnSelect.putClientProperty("tour", itemTour);
                btnSelect.addActionListener(buttonActionListener);

                panelButtons.add(btnDetail);
                panelButtons.add(btnSelect);

                // Xóa dữ liệu "Lịch trình" khỏi hàng
                tableModel.addRow(new Object[] {
                        index,
                        itemTour.getNameTour(),
                        itemTour.getDepaturePoint(),
                        itemTour.getDestination(),
                        itemTour.getMaxCapacity(),
                        itemTour.getSaleOff(),
                        panelButtons
                });
                index++;
            }
        } else if (e.getSource() == btnReset) {
            txtStartPlace.setText("");
            txtEndPlace.setText("");
            txtQuantity.setText("");
            txtStartDate.setText("");
            txtEndDate.setText("");
            tableModel.setRowCount(0);
        }
    }

    // Lắng nghe cho các nút "Xem chi tiết" và "Chọn"
    private final ActionListener buttonActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.startsWith("detail-")) {
                String tourId = command.substring(7);
                LocationDAO locationDAO = new LocationDAO();
                ArrayList<Location> locations = locationDAO.searchLocations(tourId);
                
                String itinerary = "";

                for (Location location : locations) {
                    itinerary += location.getLocation() + "-";
                }

                if (!itinerary.isEmpty()) {
                    itinerary = itinerary.substring(0, itinerary.length() - 1); // Xóa dấu "-" cuối
                }

                JOptionPane.showMessageDialog(null, "Lịch Trình: " + itinerary);
                
            } else if (command.startsWith("select-")) {
            	
            	SearchTourFrm.this.setVisible(false);
                JButton btn = (JButton) e.getSource();
                Tour selectedTour = (Tour) btn.getClientProperty("tour");
//                System.out.println(selectedTour.getNameTour());
                (new UpdateInfoTourFrm(selectedTour)).setVisible(true);
            }
        }
    };

    // Renderer cho cột chứa các nút
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                      boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof JPanel) {
                return (JPanel) value;
            }
            return this;
        }
    }

    // Editor cho cột chứa các nút
    class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel;

        public ButtonEditor(JCheckBox checkBox) {
            panel = new JPanel();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            if (value instanceof JPanel) {
                panel = (JPanel) value;
            }
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return panel;
        }
    }

}