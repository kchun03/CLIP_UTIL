package clipsoft;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

import clipsoft.getINFO.GetConNM_main;
import clipsoft.getINFO.GetFormulaList_main;
import clipsoft.getINFO.GetTableCell_main;

public class SWT_FolderChooser_Main_org
{
  private JFrame frame;
  private static JTextField textField;
  private static JTextField textField_1;
  private File selectedFile;
  private JComboBox comboBox;
  private static JLabel label_1;
  private static JLabel label_2;
  private static JLabel label_3;
  private JRadioButton rdbtnNewRadioButton;
  private JRadioButton rdbtnNewRadioButton_1;
  private JCheckBox chckbxNewCheckBox;
  private List list;

  public static void main(java.lang.String[] args)
  {
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception localException) {
    }
    EventQueue.invokeLater(new Runnable() {
      @Override
	public void run() {
        try {
          SWT_FolderChooser_Main_org window = new SWT_FolderChooser_Main_org();
          window.frame.setVisible(true);

          SWT_FolderChooser_Main_org.label_3.setVisible(false);
          SWT_FolderChooser_Main_org.textField_1.setVisible(false);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  public SWT_FolderChooser_Main_org()
  {
    initialize();
  }

  private void initialize()
  {
    this.frame = new JFrame();
    this.frame.setBounds(100, 100, 430, 376);
    this.frame.setDefaultCloseOperation(3);
    this.frame.getContentPane().setLayout(null);

    this.comboBox = new JComboBox();
    this.comboBox.setBounds(121, 72, 272, 24);
    this.comboBox.addItem("===> 선택해주세요.");
    this.comboBox.addItem("커넥션 명 찾기");
    this.comboBox.addItem("출력양식 찾기");
    this.comboBox.addItem("폰트명 찾기");
    this.comboBox.addItem("공식필드 찾기");
    this.frame.getContentPane().add(this.comboBox);

    JLabel lblNewLabel = new JLabel("경로 : ");
    lblNewLabel.setBounds(34, 25, 83, 35);
    this.frame.getContentPane().add(lblNewLabel);

    textField = new JTextField();
    textField.setBounds(121, 25, 244, 35);
    this.frame.getContentPane().add(textField);
    textField.setColumns(10);

    label_1 = new JLabel("실행 결과는 검색한 폴더에 txt 파일로 생깁니다.");

    this.rdbtnNewRadioButton = new JRadioButton("total", true);
    this.rdbtnNewRadioButton.setBounds(121, 110, 139, 27);
    this.frame.getContentPane().add(this.rdbtnNewRadioButton);

    this.rdbtnNewRadioButton_1 = new JRadioButton("each");
    this.rdbtnNewRadioButton_1.setBounds(269, 110, 124, 27);
    this.frame.getContentPane().add(this.rdbtnNewRadioButton_1);

    ButtonGroup group = new ButtonGroup();
    group.add(this.rdbtnNewRadioButton);
    group.add(this.rdbtnNewRadioButton_1);

    JButton btnNewButton = new JButton("...");
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception localException) {
    }
    this.comboBox.addActionListener(new ActionListener() {
      @Override
	public void actionPerformed(ActionEvent e) {
        System.out.println(SWT_FolderChooser_Main_org.this.comboBox.getSelectedIndex());
        if (SWT_FolderChooser_Main_org.this.comboBox.getSelectedIndex() != 0) {
          SWT_FolderChooser_Main_org.textField_1.setVisible(true);
          SWT_FolderChooser_Main_org.label_3.setVisible(true);
        } else {
          SWT_FolderChooser_Main_org.textField_1.setVisible(false);
          SWT_FolderChooser_Main_org.label_3.setVisible(false);
        }

      }

    });
    btnNewButton.addActionListener(new ActionListener() {
      @Override
	public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + "//" + "Desktop");

        fileChooser.setFileSelectionMode(1);
        int status = fileChooser.showOpenDialog(null);
        if (status == 0)
        {
          SWT_FolderChooser_Main_org.this.selectedFile = fileChooser.getSelectedFile();
          System.out.println(SWT_FolderChooser_Main_org.this.selectedFile.getAbsolutePath());
          SWT_FolderChooser_Main_org.textField.setText(SWT_FolderChooser_Main_org.this.selectedFile.getAbsolutePath());
        }

      }

    });
    btnNewButton.setBounds(367, 25, 26, 35);
    this.frame.getContentPane().add(btnNewButton);

    JButton btnNewButton_1 = new JButton("실행");
    btnNewButton_1.addActionListener(new ActionListener() {
      @Override
	public void actionPerformed(ActionEvent e) {
        java.lang.String logRadio = "";
        if (SWT_FolderChooser_Main_org.this.rdbtnNewRadioButton.isSelected()) logRadio = "total";
        else if (SWT_FolderChooser_Main_org.this.rdbtnNewRadioButton_1.isSelected()) logRadio = "each";
        if (SWT_FolderChooser_Main_org.this.comboBox.getSelectedIndex() == 0) {
          JOptionPane j1 = new JOptionPane();
          JOptionPane.showMessageDialog(null, "선택해주세요.", "알림", -1);
        } else {
          GetTableCell_main s;
          java.lang.String[] sArrays = null;
          JOptionPane j1;
          SWT_FolderChooser_Main_org.this.list = new ArrayList();
          SWT_FolderChooser_Main_org.this.list.add(SWT_FolderChooser_Main_org.textField.getText());
          SWT_FolderChooser_Main_org.this.list.add(logRadio);
          SWT_FolderChooser_Main_org.this.list.add(SWT_FolderChooser_Main_org.textField_1.getText());
          SWT_FolderChooser_Main_org.this.list.add(java.lang.String.valueOf(SWT_FolderChooser_Main_org.this.chckbxNewCheckBox.isSelected()));

          if (SWT_FolderChooser_Main_org.this.comboBox.getSelectedIndex() == 1) {
            GetConNM_main s2 = new GetConNM_main();
            try {
              sArrays = (java.lang.String[])SWT_FolderChooser_Main_org.this.list.toArray(new java.lang.String[SWT_FolderChooser_Main_org.this.list.size()]);
              GetConNM_main.main(sArrays);
              j1 = new JOptionPane();
              JOptionPane.showMessageDialog(null, "실행 완료!", "알림", -1);
            } catch (Exception e1) {
              e1.printStackTrace();
            }
          }

          if (SWT_FolderChooser_Main_org.this.comboBox.getSelectedIndex() == 2) {
        	  GetTableCell_main s3 = new GetTableCell_main();
            try {
              SWT_FolderChooser_Main_org.this.list.add("getOutputFormat");

              sArrays = (java.lang.String[])SWT_FolderChooser_Main_org.this.list.toArray(new java.lang.String[SWT_FolderChooser_Main_org.this.list.size()]);
              GetTableCell_main.main(sArrays);
              j1 = new JOptionPane();
              JOptionPane.showMessageDialog(null, "실행 완료!", "알림", -1);
            } catch (Exception e1) {
              e1.printStackTrace();
            }
          }

          if (SWT_FolderChooser_Main_org.this.comboBox.getSelectedIndex() == 3) {
            s = new GetTableCell_main();
            try {
              SWT_FolderChooser_Main_org.this.list.add("getFontList");
              sArrays = (java.lang.String[])SWT_FolderChooser_Main_org.this.list.toArray(new java.lang.String[SWT_FolderChooser_Main_org.this.list.size()]);
              GetTableCell_main.main(sArrays);
              j1 = new JOptionPane();
              JOptionPane.showMessageDialog(null, "실행 완료!", "알림", -1);
            } catch (Exception e1) {
              e1.printStackTrace();
            }
          }

          if (SWT_FolderChooser_Main_org.this.comboBox.getSelectedIndex() == 4) {
            GetFormulaList_main s4 = new GetFormulaList_main();
            try {
            	sArrays = (java.lang.String[])SWT_FolderChooser_Main_org.this.list.toArray(new java.lang.String[SWT_FolderChooser_Main_org.this.list.size()]);
              GetFormulaList_main.main(sArrays);
              j1 = new JOptionPane();
              JOptionPane.showMessageDialog(null, "실행 완료!", "알림", -1);
            } catch (Exception e1) {
              e1.printStackTrace();
            }
          }
        }
      }

    });
    btnNewButton_1.setBounds(332, 250, 61, 35);
    this.frame.getContentPane().add(btnNewButton_1);

    JLabel label = new JLabel("기능 : ");
    label.setBounds(34, 67, 83, 35);
    this.frame.getContentPane().add(label);

    label_1.setBounds(34, 297, 304, 35);
    this.frame.getContentPane().add(label_1);

    label_2 = new JLabel("LogType : ");
    label_2.setBounds(34, 106, 83, 35);
    this.frame.getContentPane().add(label_2);

    label_3 = new JLabel("검색어 : ");
    label_3.setBounds(34, 200, 83, 35);
    this.frame.getContentPane().add(label_3);

    textField_1 = new JTextField();
    textField_1.setColumns(10);
    textField_1.setBounds(121, 201, 244, 35);
    this.frame.getContentPane().add(textField_1);

    JLabel label_4 = new JLabel("중복제거 : ");
    label_4.setBounds(34, 153, 83, 35);
    this.frame.getContentPane().add(label_4);

    this.chckbxNewCheckBox = new JCheckBox("");
    this.chckbxNewCheckBox.setSelected(true);
    this.chckbxNewCheckBox.setBounds(129, 157, 26, 27);
    this.frame.getContentPane().add(this.chckbxNewCheckBox);
  }
}