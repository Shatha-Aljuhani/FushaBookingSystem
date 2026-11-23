package com.mycompany.fushabookingsystem;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudySpacesFrame extends JFrame {

    private final Color navy = new Color(48, 59, 145);
    private final Color teal = new Color(32, 163, 166);
    private final Font font = new Font("Segoe UI", Font.PLAIN, 15);

    private final DefaultTableModel spacesModel = new DefaultTableModel(
            new String[]{"رقم المكان", "نوع المكان", "الموقع", "السعة", "الحالة"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public StudySpacesFrame() {
        setTitle("FUSHA - Study Spaces & Booking");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBackground(new Color(245, 250, 255));
        root.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));
        setContentPane(root);

        JLabel heading = new JLabel(
                "إدارة أماكن المذاكرة والحجوزات",
                SwingConstants.CENTER
        );
        heading.setFont(new Font("Segoe UI", Font.BOLD, 27));
        heading.setForeground(navy);
        root.add(heading, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 17));
        tabs.setSelectedIndex(0);

        tabs.addTab("إدارة أماكن المذاكرة", buildSpacesTab());
        tabs.addTab("تسجيل حجز جديد", buildBookingTab());

        root.add(tabs, BorderLayout.CENTER);

        JButton back = button("العودة للرئيسية", navy);

        back.addActionListener(e -> {
            new MainDashboard().setVisible(true);
            dispose();
        });

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottom.setOpaque(false);
        bottom.add(back);
        root.add(bottom, BorderLayout.SOUTH);
    }

    private JPanel buildSpacesTab() {

        JPanel page = page();

        JTable tblSpaces = new JTable(spacesModel);
        tblSpaces.setRowHeight(28);
        tblSpaces.setFont(font);

        page.add(new JScrollPane(tblSpaces), BorderLayout.CENTER);

        JPanel fields = new JPanel(new GridLayout(2, 5, 10, 5));
        fields.setOpaque(false);

        JTextField txtSpaceId = field();

        JComboBox<String> cmbType = new JComboBox<>(
                new String[]{"مقعد فردي", "غرفة جماعية"}
        );

        JTextField txtLocation = field();
        JTextField txtCapacity = field();

        JComboBox<String> cmbStatus = new JComboBox<>(
                new String[]{"متاح", "خارج الخدمة"}
        );

        fields.add(label("رقم المكان"));
        fields.add(label("نوع المكان"));
        fields.add(label("الموقع"));
        fields.add(label("السعة"));
        fields.add(label("الحالة"));

        fields.add(txtSpaceId);
        fields.add(cmbType);
        fields.add(txtLocation);
        fields.add(txtCapacity);
        fields.add(cmbStatus);

        JPanel actions = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 12, 8)
        );
        actions.setOpaque(false);

        JButton add = button("إضافة مكان", teal);
        JButton update = button("تعديل مكان", teal);
        JButton delete = button("حذف مكان", navy);
        JButton refresh = button("عرض الأماكن", teal);

        actions.add(add);
        actions.add(update);
        actions.add(delete);
        actions.add(refresh);

        add.addActionListener(e -> pending());
        update.addActionListener(e -> pending());
        delete.addActionListener(e -> pending());
        refresh.addActionListener(e -> pending());

        tblSpaces.getSelectionModel().addListSelectionListener(e -> {

            int row = tblSpaces.getSelectedRow();

            if (!e.getValueIsAdjusting() && row >= 0) {

                txtSpaceId.setText(
                        String.valueOf(tblSpaces.getValueAt(row, 0))
                );

                cmbType.setSelectedItem(
                        tblSpaces.getValueAt(row, 1)
                );

                txtLocation.setText(
                        String.valueOf(tblSpaces.getValueAt(row, 2))
                );

                txtCapacity.setText(
                        String.valueOf(tblSpaces.getValueAt(row, 3))
                );

                cmbStatus.setSelectedItem(
                        tblSpaces.getValueAt(row, 4)
                );
            }
        });

        JPanel top = new JPanel(new BorderLayout(5, 5));
        top.setOpaque(false);

        top.add(fields, BorderLayout.CENTER);
        top.add(actions, BorderLayout.SOUTH);

        page.add(top, BorderLayout.NORTH);

        return page;
    }

    private JPanel buildBookingTab() {

        JPanel page = page();

        JPanel fields = new JPanel(new GridLayout(6, 2, 12, 12));
        fields.setOpaque(false);

        JTextField txtStudentId = field();
        JTextField txtStudentName = field();

        JComboBox<String> cmbSpace = new JComboBox<>(
                new String[]{
                    "اختر مكانًا (يُحمّل من قاعدة البيانات لاحقًا)"
                }
        );

        JTextField txtDate = field();
        JTextField txtStart = field();
        JTextField txtEnd = field();

        fields.add(label("الرقم الجامعي"));
        fields.add(txtStudentId);

        fields.add(label("اسم الطالبة"));
        fields.add(txtStudentName);

        fields.add(label("المكان"));
        fields.add(cmbSpace);

        fields.add(label("التاريخ (YYYY-MM-DD)"));
        fields.add(txtDate);

        fields.add(label("وقت البداية (HH:MM)"));
        fields.add(txtStart);

        fields.add(label("وقت النهاية (HH:MM)"));
        fields.add(txtEnd);

        JPanel center = new JPanel(new BorderLayout(15, 12));
        center.setOpaque(false);

        center.setBorder(
                BorderFactory.createEmptyBorder(15, 170, 15, 170)
        );

        center.add(fields, BorderLayout.NORTH);

        JLabel note = label(
                "سيتم فحص تعارض الحجوزات عند ربط Apache Derby."
        );

        note.setHorizontalAlignment(SwingConstants.CENTER);
        center.add(note, BorderLayout.CENTER);

        JButton book = button("تسجيل الحجز", teal);
        book.addActionListener(e -> pending());

        JPanel action = new JPanel(new FlowLayout(FlowLayout.CENTER));
        action.setOpaque(false);
        action.add(book);

        center.add(action, BorderLayout.SOUTH);
        page.add(center, BorderLayout.CENTER);

        return page;
    }

    private JPanel page() {

        JPanel page = new JPanel(new BorderLayout(10, 12));

        page.setBorder(
                BorderFactory.createEmptyBorder(14, 14, 14, 14)
        );

        page.setBackground(Color.WHITE);

        return page;
    }

    private JLabel label(String text) {

        JLabel l = new JLabel(text, SwingConstants.RIGHT);
        l.setFont(font);
        l.setForeground(navy);

        return l;
    }

    private JTextField field() {

        JTextField f = new JTextField();
        f.setFont(font);

        return f;
    }

    private JButton button(String text, Color color) {

        JButton b = new JButton(text);

        b.setFont(new Font("Segoe UI", Font.BOLD, 15));
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);

        b.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );

        return b;
    }

    private void pending() {

        JOptionPane.showMessageDialog(
                this,
                "هذه الواجهة جاهزة. تنفيذ العملية يحتاج ربط قاعدة بيانات Derby أولًا."
        );
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new StudySpacesFrame().setVisible(true)
        );
    }
}