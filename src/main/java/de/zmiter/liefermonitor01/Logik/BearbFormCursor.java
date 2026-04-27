/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.zmiter.liefermonitor01.Logik;

import de.zmiter.liefermonitor01.DBObjekte.Lieferung;
import java.awt.Color;
import java.awt.Event;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

/**
 *
 * @author lepeschko
 */
public class BearbFormCursor extends javax.swing.JFrame{
    
    /**
     * Creates new form BearbForm
     */
    String person = "pers__";
    String beschreibung = "Thyssen Schachtbau GmbH  ";
    int menge = 1;
    Lieferung lieferung = new Lieferung();
    String name = ""; // здесь мы будем представлять название поставки или товара
    
    // Константы для цветов
    private static final Color VALID_DATE_COLOR = new Color(204, 255, 204); // светло-зеленый
    private static final Color INVALID_DATE_COLOR = new Color(255, 204, 204); // светло-красный
    private static final Color DEFAULT_COLOR = new Color(255, 255, 255); // белый
    private JComboBox jComboBoxEinhMenge;
    
    public BearbFormCursor() {
        this.name = lieferung.getText() + "--- Face-02";
     
        startComponents();
      
        // Инициализация валидации дат
        initializeDateValidation();
        
        backgroundCombo( jComboBox2);
         backgroundCombo( jComboBox3);
          backgroundCombo( jComboBox4);
        
    }

    /**
     * Инициализация валидации для всех полей с датами
     */
    private void initializeDateValidation() {
        // Валидация для jFormattedTextField1 (Datum der Anforderung)
        setupDateValidation(jFormattedTextField1, "dd.MM.yy");
        
        // Валидация для jFormattedTextField2 (Wunschliefertermin)
        setupDateValidation(jFormattedTextField2, "dd.MM.yy");
        
        // Валидация для jFormattedTextField4 (Bestellt am)
        setupDateValidation(jFormattedTextField4, "dd.MM.yy");
        
        // Валидация для jFormattedTextField5 (Bestellt am)
        setupDateValidation(jFormattedTextField5, "dd.MM.yy");
        
        // Валидация для jFormattedTextField6 (Wareneingang TS nach Plan)
        setupDateValidation(jFormattedTextField6, "dd.MM.yy");
        
        // Валидация для jFormattedTextField7 (Wareneingang TS erfolgt am)
        setupDateValidation(jFormattedTextField7, "dd.MM.yy");
        
        // Валидация для jFormattedTextField8 (Lieferung an GR erfolgt am)
        setupDateValidation(jFormattedTextField8, "dd.MM.yy");
        
        // Валидация для jFormattedTextField9 (Wareneingang erfolgt am)
        setupDateValidation(jFormattedTextField9, "dd.MM.yy");
        
        // Валидация для jFormattedTextField10 (Datum)
        setupDateValidation(jFormattedTextField10, "dd.MM.yy");
    }
    
    /**
     * Настройка валидации даты для конкретного поля
     * @param field поле для валидации
     * @param dateFormat формат даты
     */
    private void setupDateValidation(JFormattedTextField field, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateDateField(field, sdf);
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                validateDateField(field, sdf);
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
                validateDateField(field, sdf);
            }
        });
    }
    
    /**
     * Валидация поля даты с визуальной обратной связью
     * @param field поле для валидации
     * @param sdf форматтер даты
     */
    private void validateDateField(JFormattedTextField field, SimpleDateFormat sdf) {
        String text = field.getText().trim();
        
        if (text.isEmpty()) {
            field.setBackground(DEFAULT_COLOR);
            return;
        }
        
        try {
            Date date = sdf.parse(text);
            field.setBackground(VALID_DATE_COLOR);
            field.setToolTipText("Валидная дата: " + sdf.format(date));
        } catch (ParseException ex) {
            field.setBackground(INVALID_DATE_COLOR);
            field.setToolTipText("Неверный формат даты. Используйте формат: " + sdf.toPattern());
        }
    }

    public void backgroundCombo( JComboBox box){
         if(box.getSelectedIndex() == 1){
            box.setBackground(Color.yellow);
        }else if(box.getSelectedIndex() == 0){
            box.setBackground(Color.red);
        }else if(box.getSelectedIndex() == 2){
            box.setBackground(Color.green);
        }
    }
   
    /**
     * Улучшенная валидация даты с более подробной информацией
     * @param str строка для валидации
     * @return true если дата валидна
     */
    boolean isDateOK(String str){
       if (str == null || str.trim().isEmpty()) {
           return false;
       }
       
       boolean isDate = false;
       int ab = 0;
       int cd = 0;
       int ef = 0;
       
       try{ 
           System.out.println("Проверка даты: " + str);
           
           if (str.length() != 8) {
               System.out.println("Неверная длина строки даты. Ожидается 8 символов, получено: " + str.length());
               return false;
           }
           
           ab = Integer.parseInt(str.substring(0, 2));
           cd = Integer.parseInt(str.substring(3, 5));
           ef = Integer.parseInt(str.substring(6, 8));
           
           // Проверка валидности дня, месяца и года
           if (ab < 1 || ab > 31) {
               System.out.println("Неверный день: " + ab);
               return false;
           }
           
           if (cd < 1 || cd > 12) {
               System.out.println("Неверный месяц: " + cd);
               return false;
           }
           
           if (ef < 0 || ef > 99) {
               System.out.println("Неверный год: " + ef);
               return false;
           }
           
           isDate = true;
           System.out.println("Дата валидна: " + ab + "." + cd + "." + ef);
          
       } catch(NumberFormatException numEx){
           System.out.println("Ошибка парсинга даты: " + numEx.getMessage());
           numEx.printStackTrace();
       } catch(StringIndexOutOfBoundsException strEx) {
           System.out.println("Ошибка индекса строки: " + strEx.getMessage());
       }
       
      return isDate;
   }
            
       
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    public void startComponents(){
        
        jPanel1 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jPanel31 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        tFAgreementNR = new javax.swing.JTextField();
        jPanel29 = new javax.swing.JPanel();
        jFormattedTextField17 = new javax.swing.JFormattedTextField();
        jComboBox12 = new javax.swing.JComboBox<>();
        jPanel50 = new javax.swing.JPanel();
        jPanel49 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel52 = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jPanel7 = new javax.swing.JPanel();
        tFKommentar01 = new javax.swing.JTextField();
        jPanel51 = new javax.swing.JPanel();
        jFormattedTextField8 = new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel53 = new javax.swing.JPanel();
        tFAnforderungNR = new javax.swing.JTextField();
        jPanel54 = new javax.swing.JPanel();
        jFormattedTextField5 = new javax.swing.JFormattedTextField();
        jPanel55 = new javax.swing.JPanel();
        jFormattedTextField9 = new javax.swing.JFormattedTextField();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jFormattedTextField4 = new javax.swing.JFormattedTextField();
        jPanel14 = new javax.swing.JPanel();
        jTextField4 = new javax.swing.JTextField();
        jPanel56 = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jFormattedTextField16 = new javax.swing.JFormattedTextField();
        jComboBox11 = new javax.swing.JComboBox<>();
        jPanel17 = new javax.swing.JPanel();
        jFormattedTextField6 = new javax.swing.JFormattedTextField();
        jPanel36 = new javax.swing.JPanel();
        jFormattedTextField15 = new javax.swing.JFormattedTextField();
        jComboBox9 = new javax.swing.JComboBox<>();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jPanel57 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel58 = new javax.swing.JPanel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jPanel37 = new javax.swing.JPanel();
        jTextField3 = new javax.swing.JTextField();
        jComboBox10 = new javax.swing.JComboBox<>();
        jPanel21 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jTextField17 = new javax.swing.JTextField();
        jPanel23 = new javax.swing.JPanel();
        jFormattedTextField7 = new javax.swing.JFormattedTextField();
        jPanel38 = new javax.swing.JPanel();
        jTextField14 = new javax.swing.JTextField();
        jPanel24 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jFormattedTextField10 = new javax.swing.JFormattedTextField();
        jPanel26 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        jPanel35 = new javax.swing.JPanel();
        jTextField15 = new javax.swing.JTextField();
        jPanel32 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jFormattedTextField11 = new javax.swing.JFormattedTextField();
        jComboBox5 = new javax.swing.JComboBox<>();
        jPanel34 = new javax.swing.JPanel();
        jTextField6 = new javax.swing.JTextField();
        jPanel39 = new javax.swing.JPanel();
        jTextField16 = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jFormattedTextField12 = new javax.swing.JFormattedTextField();
        jComboBox6 = new javax.swing.JComboBox<>();
        jPanel11 = new javax.swing.JPanel();
        jPanel59 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel60 = new javax.swing.JPanel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jPanel40 = new javax.swing.JPanel();
        jPanel41 = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        jFormattedTextField13 = new javax.swing.JFormattedTextField();
        jComboBox7 = new javax.swing.JComboBox<>();
        jPanel43 = new javax.swing.JPanel();
        jTextField8 = new javax.swing.JTextField();
        jPanel44 = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jPanel46 = new javax.swing.JPanel();
        jFormattedTextField14 = new javax.swing.JFormattedTextField();
        jComboBox8 = new javax.swing.JComboBox<>();
        jPanel47 = new javax.swing.JPanel();
        jTextField9 = new javax.swing.JTextField();
        jPanel48 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        
        jComboBoxEinhMenge = new JComboBox();
        

        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        setTitle(name);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setFocusCycleRoot(false);
        setFocusTraversalPolicyProvider(true);
        setLocation(new java.awt.Point(100, 100));
        setMaximumSize(new java.awt.Dimension(1200, 800));
        setMinimumSize(new java.awt.Dimension(500, 400));
        setPreferredSize(new java.awt.Dimension(1000, 750));
        setSize(new java.awt.Dimension(1000, 700));
        setType(java.awt.Window.Type.UTILITY);

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setMaximumSize(new java.awt.Dimension(900, 100));
        jPanel1.setMinimumSize(new java.awt.Dimension(500, 50));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 150));
        jPanel1.setLayout(new java.awt.GridLayout(2, 1));

        jPanel30.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 102), new java.awt.Color(255, 255, 51), null, null));
        jPanel30.setLayout(new java.awt.GridLayout(2, 3, 1, 1));

        jCheckBox2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox2.setText("neu");
        jCheckBox2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel30.add(jCheckBox2);

        jCheckBox3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox3.setText("aktiv");
        jCheckBox3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel30.add(jCheckBox3);

        jCheckBox4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox4.setText("MH unterschrieben");
        jCheckBox4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel30.add(jCheckBox4);

        jCheckBox5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox5.setText("Unterschr. Kunde");
        jCheckBox5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel30.add(jCheckBox5);

        jCheckBox6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox6.setText("an Einkauf weitergeleitet");
        jCheckBox6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel30.add(jCheckBox6);

        jCheckBox7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox7.setText("Investition/Leasing");
        jPanel30.add(jCheckBox7);

        jPanel1.add(jPanel30);

        jPanel31.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel31.setMinimumSize(new java.awt.Dimension(109, 160));
        jPanel31.setLayout(new java.awt.GridLayout(1, 4, 1, 1));

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DE", "EU", "Sonst", "unbekannt" }));
        jComboBox1.setToolTipText("");
        jComboBox1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Herkunft", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel31.add(jComboBox1);

        jFormattedTextField3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Menge", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFormattedTextField3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
         jComboBoxEinhMenge.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBoxEinhMenge.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "St.", "Kg", "Tonne", "M3", "M2","M" }));
        jComboBoxEinhMenge.setToolTipText("");
        jComboBoxEinhMenge.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Einheit", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       
        
        jPanel31.add(jFormattedTextField3);
         jPanel31.add(jComboBoxEinhMenge);

        jFormattedTextField2.setBackground(new java.awt.Color(255, 204, 204));
        jFormattedTextField2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wunschliefertermin", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
		final java.text.SimpleDateFormat sdf_ddMMyy = new java.text.SimpleDateFormat("dd.MM.yy");
		sdf_ddMMyy.setLenient(false);
		jFormattedTextField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(sdf_ddMMyy)));
		jFormattedTextField2.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
			public void insertUpdate(javax.swing.event.DocumentEvent e) { validateDate(); }
			public void removeUpdate(javax.swing.event.DocumentEvent e) { validateDate(); }
			public void changedUpdate(javax.swing.event.DocumentEvent e) { validateDate(); }
			private void validateDate() {
				String text = jFormattedTextField2.getText().trim();
				if (text.isEmpty()) {
					jFormattedTextField2.setBackground(new java.awt.Color(255, 204, 204));
					return;
				}
				try {
					sdf_ddMMyy.parse(text);
					jFormattedTextField2.setBackground(new java.awt.Color(204, 255, 204));
				} catch (java.text.ParseException ex) {
					jFormattedTextField2.setBackground(new java.awt.Color(255, 204, 204));
				}
			}
		});
        jPanel31.add(jFormattedTextField2);

        jPanel1.add(jPanel31);

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setMaximumSize(new java.awt.Dimension(900, 80));
        jPanel4.setMinimumSize(new java.awt.Dimension(500, 50));
        jPanel4.setName(""); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(800, 70));

        jScrollPane1.setViewportBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(238, 70));

        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(10);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Beschreibung", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jTextArea1.setMaximumSize(new java.awt.Dimension(2000, 2000));
        jTextArea1.setMinimumSize(new java.awt.Dimension(232, 50));
        jTextArea1.setName(""); // NOI18N
        jTextArea1.setPreferredSize(new java.awt.Dimension(700, 70));
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel9.setAutoscrolls(true);
        jPanel9.setMaximumSize(new java.awt.Dimension(1000, 700));
        jPanel9.setMinimumSize(new java.awt.Dimension(600, 100));
        jPanel9.setPreferredSize(new java.awt.Dimension(900, 400));
        jPanel9.setRequestFocusEnabled(false);
        jPanel9.setVerifyInputWhenFocusTarget(false);
        jPanel9.setLayout(new java.awt.BorderLayout(1, 1));

        jScrollPane2.setViewportBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane2.setMaximumSize(new java.awt.Dimension(1200, 1000));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(600, 160));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(900, 300));
        jScrollPane2.setViewportView(jPanel2);

        jPanel2.setAutoscrolls(true);
        jPanel2.setFocusCycleRoot(true);
        jPanel2.setFocusTraversalPolicyProvider(true);
        jPanel2.setMaximumSize(new java.awt.Dimension(1200, 1000));
        jPanel2.setMinimumSize(new java.awt.Dimension(500, 300));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(600, 800));
        jPanel2.setLayout(new java.awt.GridLayout(12, 1, 1, 1));

        jPanel27.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel28.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 102), new java.awt.Color(0, 153, 102), null, null));
        jPanel28.setLayout(new java.awt.BorderLayout());

        tFAgreementNR.setText("jTextField11");
        tFAgreementNR.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Agreement Nr.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel28.add(tFAgreementNR, java.awt.BorderLayout.CENTER);

        jPanel27.add(jPanel28);

        jPanel29.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel29.setLayout(new javax.swing.BoxLayout(jPanel29, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Preis", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField17.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        jFormattedTextField17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jFormattedTextField17MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jFormattedTextField17MouseExited(evt);
            }
        });
        jFormattedTextField17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField17KeyPressed(evt);
            }
        });
        jPanel29.add(jFormattedTextField17);

        jComboBox12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Euro", "USD", "Others" }));
        jComboBox12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Währung", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel29.add(jComboBox12);

        jPanel27.add(jPanel29);

        jPanel50.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));

        jPanel49.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel49.setLayout(new javax.swing.BoxLayout(jPanel49, javax.swing.BoxLayout.LINE_AXIS));

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setText("RS");
        jButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 51), new java.awt.Color(0, 204, 102), null, null));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton1MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
        });
        jPanel49.add(jButton1);

        jPanel52.setLayout(new javax.swing.BoxLayout(jPanel52, javax.swing.BoxLayout.LINE_AXIS));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Rot", "Gelb", "Grün" }));
        jComboBox2.setSelectedIndex(1);
        jComboBox2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Status Anlieferung GR", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel52.add(jComboBox2);

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel52, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE))
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addComponent(jPanel52, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addComponent(jPanel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel27.add(jPanel50);

        jPanel2.add(jPanel27);

        jPanel5.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 102), new java.awt.Color(0, 153, 102), null, null));
        jPanel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datum der Anforderung", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField1.setToolTipText("Datum in Format dd.mm.yy");
        jFormattedTextField1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel6.add(jFormattedTextField1);

        jPanel5.add(jPanel6);

        jPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));

        tFKommentar01.setText("jTextField13");
        tFKommentar01.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lieferant", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel7.add(tFKommentar01);

        jPanel5.add(jPanel7);

        jPanel51.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel51.setLayout(new javax.swing.BoxLayout(jPanel51, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lieferung an GR erfolgt am", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField8.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField8KeyPressed(evt);
            }
        });
        jPanel51.add(jFormattedTextField8);

        jPanel5.add(jPanel51);

        jPanel2.add(jPanel5);

        jPanel3.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel53.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 102), new java.awt.Color(0, 153, 102), null, null));
        jPanel53.setLayout(new javax.swing.BoxLayout(jPanel53, javax.swing.BoxLayout.LINE_AXIS));

        tFAnforderungNR.setText("jTextField12");
        tFAnforderungNR.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Anforderung Nr.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel53.add(tFAnforderungNR);

        jPanel3.add(jPanel53);

        jPanel54.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bestellt am (dd.mm.yy)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel54.setLayout(new javax.swing.BoxLayout(jPanel54, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField5.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField5KeyPressed(evt);
            }
        });
        jPanel54.add(jFormattedTextField5);

        jPanel3.add(jPanel54);

        jPanel55.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel55.setLayout(new javax.swing.BoxLayout(jPanel55, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wareneingang erfolgt am", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField9.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField9KeyPressed(evt);
            }
        });
        jPanel55.add(jFormattedTextField9);

        jPanel3.add(jPanel55);

        jPanel2.add(jPanel3);

        jPanel12.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 102), new java.awt.Color(0, 153, 102), null, null));
        jPanel13.setLayout(new javax.swing.BoxLayout(jPanel13, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bestellt am (dd.mm.yy)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField4.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField4KeyPressed(evt);
            }
        });
        jPanel13.add(jFormattedTextField4);

        jPanel12.add(jPanel13);

        jPanel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel14.setLayout(new java.awt.BorderLayout());

        jTextField4.setText("jTextField4");
        jTextField4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bestellungsnummer", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel14.add(jTextField4, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel14);

        jPanel56.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel56.setLayout(new javax.swing.BoxLayout(jPanel56, javax.swing.BoxLayout.LINE_AXIS));

        jTextField5.setText("jTextField5");
        jTextField5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kommentar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel56.add(jTextField5);

        jPanel12.add(jPanel56);

        jPanel2.add(jPanel12);

        jPanel15.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel15.setLayout(new java.awt.GridLayout(1, 3));

        jPanel16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 102), new java.awt.Color(0, 153, 102), null, null));
        jPanel16.setLayout(new javax.swing.BoxLayout(jPanel16, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Summe für Material und Leistungen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField16.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        jFormattedTextField16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jFormattedTextField16MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jFormattedTextField16MouseExited(evt);
            }
        });
        jFormattedTextField16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField16KeyPressed(evt);
            }
        });
        jPanel16.add(jFormattedTextField16);

        jComboBox11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Euro", "USD", "Others" }));
        jComboBox11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Währung", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel16.add(jComboBox11);

        jPanel15.add(jPanel16);

        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel17.setLayout(new javax.swing.BoxLayout(jPanel17, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wareneingang TS nach Plan (dd.mm.yy)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField6.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField6KeyPressed(evt);
            }
        });
        jPanel17.add(jFormattedTextField6);

        jPanel15.add(jPanel17);

        jPanel36.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel36.setLayout(new javax.swing.BoxLayout(jPanel36, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Gewicht", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField15.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        jFormattedTextField15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField15KeyPressed(evt);
            }
        });
        jPanel36.add(jFormattedTextField15);

        jComboBox9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tonnen", "Kg", "" }));
        jComboBox9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Einh.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel36.add(jComboBox9);

        jPanel15.add(jPanel36);

        jPanel2.add(jPanel15);

        jPanel18.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel19.setBackground(new java.awt.Color(255, 255, 204));
        jPanel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, new java.awt.Color(204, 255, 204)));
        jPanel19.setLayout(new java.awt.BorderLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 204));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setLabelFor(jPanel19);
        jLabel1.setText("<Abrechnung mit Kunden>");
        jLabel1.setAlignmentX(0.5F);
        jLabel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 51, 0), java.awt.Color.gray, new java.awt.Color(255, 255, 51), new java.awt.Color(255, 255, 204)));
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setMinimumSize(new java.awt.Dimension(100, 10));
        jLabel1.setOpaque(true);
        jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel19.add(jLabel1, java.awt.BorderLayout.CENTER);

        jPanel18.add(jPanel19);

        jPanel20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));

        jPanel57.setLayout(new javax.swing.BoxLayout(jPanel57, javax.swing.BoxLayout.LINE_AXIS));

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setText("RS");
        jButton2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton2MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton2MousePressed(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel57.add(jButton2);

        jPanel58.setLayout(new javax.swing.BoxLayout(jPanel58, javax.swing.BoxLayout.LINE_AXIS));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Rot", "Gelb", "Grün" }));
        jComboBox3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Status Auslieferung an TS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel58.add(jComboBox3);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jPanel57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel58, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jPanel57, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel18.add(jPanel20);

        jPanel37.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel37.setLayout(new javax.swing.BoxLayout(jPanel37, javax.swing.BoxLayout.LINE_AXIS));

        jTextField3.setText("jTextField3");
        jTextField3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "L x B x H", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel37.add(jTextField3);

        jComboBox10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "CM", "MM" }));
        jComboBox10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Einh.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel37.add(jComboBox10);

        jPanel18.add(jPanel37);

        jPanel2.add(jPanel18);

        jPanel21.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel21.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel22.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, null));
        jPanel22.setLayout(new javax.swing.BoxLayout(jPanel22, javax.swing.BoxLayout.LINE_AXIS));

        jTextField17.setText("jTextField17");
        jTextField17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "RG-Nummer an AG", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel22.add(jTextField17);

        jPanel21.add(jPanel22);

        jPanel23.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel23.setLayout(new javax.swing.BoxLayout(jPanel23, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wareneingang TS erfolgt am", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField7.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField7KeyPressed(evt);
            }
        });
        jPanel23.add(jFormattedTextField7);

        jPanel21.add(jPanel23);

        jPanel38.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel38.setLayout(new javax.swing.BoxLayout(jPanel38, javax.swing.BoxLayout.LINE_AXIS));

        jTextField14.setText("jTextField14");
        jTextField14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kolli", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel38.add(jTextField14);

        jPanel21.add(jPanel38);

        jPanel2.add(jPanel21);

        jPanel24.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel25.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, null));
        jPanel25.setLayout(new javax.swing.BoxLayout(jPanel25, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datum", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField10.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField10KeyPressed(evt);
            }
        });
        jPanel25.add(jFormattedTextField10);

        jPanel24.add(jPanel25);

        jPanel26.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel26.setLayout(new javax.swing.BoxLayout(jPanel26, javax.swing.BoxLayout.LINE_AXIS));

        jTextField2.setText("jTextField2");
        jTextField2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wareneingang TS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel26.add(jTextField2);

        jPanel24.add(jPanel26);

        jPanel35.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel35.setLayout(new javax.swing.BoxLayout(jPanel35, javax.swing.BoxLayout.LINE_AXIS));

        jTextField15.setText("jTextField15");
        jTextField15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "PL", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel35.add(jTextField15);

        jPanel24.add(jPanel35);

        jPanel2.add(jPanel24);

        jPanel32.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel33.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, null));
        jPanel33.setLayout(new javax.swing.BoxLayout(jPanel33, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Weitergerechnet an AG", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField11.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        jFormattedTextField11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jFormattedTextField11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jFormattedTextField11MouseExited(evt);
            }
        });
        jFormattedTextField11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField11KeyPressed(evt);
            }
        });
        jPanel33.add(jFormattedTextField11);

        jComboBox5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Euro", "USD", "DK", "Other" }));
        jComboBox5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Währung", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel33.add(jComboBox5);

        jPanel32.add(jPanel33);

        jPanel34.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel34.setLayout(new javax.swing.BoxLayout(jPanel34, javax.swing.BoxLayout.LINE_AXIS));

        jTextField6.setText("jTextField6");
        jTextField6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Auslieferung nach Plan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel34.add(jTextField6);

        jPanel32.add(jPanel34);

        jPanel39.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel39.setLayout(new javax.swing.BoxLayout(jPanel39, javax.swing.BoxLayout.LINE_AXIS));

        jTextField16.setText("jTextField16");
        jTextField16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Container", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel39.add(jTextField16);

        jPanel32.add(jPanel39);

        jPanel2.add(jPanel32);

        jPanel8.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel10.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, null));
        jPanel10.setLayout(new javax.swing.BoxLayout(jPanel10, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Angebotssumme weitergerechnet", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField12.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        jFormattedTextField12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jFormattedTextField12MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jFormattedTextField12MouseExited(evt);
            }
        });
        jFormattedTextField12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField12KeyPressed(evt);
            }
        });
        jPanel10.add(jFormattedTextField12);

        jComboBox6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Euro", "USD", "DK", "Others" }));
        jComboBox6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Währung", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel10.add(jComboBox6);

        jPanel8.add(jPanel10);

        jPanel11.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));

        jPanel59.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel59.setLayout(new javax.swing.BoxLayout(jPanel59, javax.swing.BoxLayout.LINE_AXIS));

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setText("RS");
        jButton3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton3MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton3MousePressed(evt);
            }
        });
        jPanel59.add(jButton3);

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Rot", "Gelb", "Grün" }));
        jComboBox4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Status Auslieferung DNK", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        javax.swing.GroupLayout jPanel60Layout = new javax.swing.GroupLayout(jPanel60);
        jPanel60.setLayout(jPanel60Layout);
        jPanel60Layout.setHorizontalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 207, Short.MAX_VALUE)
            .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jComboBox4, javax.swing.GroupLayout.Alignment.TRAILING, 0, 261, Short.MAX_VALUE))
        );
        jPanel60Layout.setVerticalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel60Layout.createSequentialGroup()
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jPanel59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel59, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                    .addComponent(jPanel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );

        jPanel8.add(jPanel11);

        jPanel40.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel40.setLayout(new javax.swing.BoxLayout(jPanel40, javax.swing.BoxLayout.LINE_AXIS));
        jPanel8.add(jPanel40);

        jPanel2.add(jPanel8);

        jPanel41.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel42.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, null));
        jPanel42.setLayout(new javax.swing.BoxLayout(jPanel42, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Angebotssumme nicht weitergerechnet", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField13.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        jFormattedTextField13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jFormattedTextField13MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jFormattedTextField13MouseExited(evt);
            }
        });
        jFormattedTextField13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField13KeyPressed(evt);
            }
        });
        jPanel42.add(jFormattedTextField13);

        jComboBox7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Euro", "USD", "Others" }));
        jComboBox7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Währung", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel42.add(jComboBox7);

        jPanel41.add(jPanel42);

        jPanel43.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel43.setLayout(new javax.swing.BoxLayout(jPanel43, javax.swing.BoxLayout.LINE_AXIS));

        jTextField8.setText("jTextField8");
        jTextField8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Zahlungsbedingungen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel43.add(jTextField8);

        jPanel41.add(jPanel43);

        jPanel44.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel44.setLayout(new javax.swing.BoxLayout(jPanel44, javax.swing.BoxLayout.LINE_AXIS));
        jPanel41.add(jPanel44);

        jPanel2.add(jPanel41);

        jPanel45.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel46.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, null));
        jPanel46.setLayout(new javax.swing.BoxLayout(jPanel46, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kunden in Rechnung stellen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField14.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        jFormattedTextField14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jFormattedTextField14MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jFormattedTextField14MouseExited(evt);
            }
        });
        jFormattedTextField14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField14KeyPressed(evt);
            }
        });
        jPanel46.add(jFormattedTextField14);

        jComboBox8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Euro", "USD", "Others" }));
        jComboBox8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Währung", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel46.add(jComboBox8);

        jPanel45.add(jPanel46);

        jPanel47.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel47.setLayout(new javax.swing.BoxLayout(jPanel47, javax.swing.BoxLayout.LINE_AXIS));

        jTextField9.setText("jTextField9");
        jTextField9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kommentar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel47.add(jTextField9);

        jPanel45.add(jPanel47);

        jPanel48.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel48.setLayout(new javax.swing.BoxLayout(jPanel48, javax.swing.BoxLayout.LINE_AXIS));
        jPanel45.add(jPanel48);

        jPanel2.add(jPanel45);

        jScrollPane2.setViewportView(jPanel2);

        jPanel9.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jMenuBar1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jMenu1.setText("File");

        jMenuItem1.setText("Speichern in DB");
        jMenuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jMenuItem1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jMenuItem1MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem1MousePressed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jMenuItem2.setText("Währungen");
        jMenuItem2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jMenuItem2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jMenuItem2MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem2MousePressed(evt);
            }
        });
        jMenuItem2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenuItem2KeyPressed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jPanel31 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        tFAgreementNR = new javax.swing.JTextField();
        jPanel29 = new javax.swing.JPanel();
        jFormattedTextField17 = new javax.swing.JFormattedTextField();
        jComboBox12 = new javax.swing.JComboBox<>();
        jPanel50 = new javax.swing.JPanel();
        jPanel49 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel52 = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jPanel7 = new javax.swing.JPanel();
        tFKommentar01 = new javax.swing.JTextField();
        jPanel51 = new javax.swing.JPanel();
        jFormattedTextField8 = new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel53 = new javax.swing.JPanel();
        tFAnforderungNR = new javax.swing.JTextField();
        jPanel54 = new javax.swing.JPanel();
        jFormattedTextField5 = new javax.swing.JFormattedTextField();
        jPanel55 = new javax.swing.JPanel();
        jFormattedTextField9 = new javax.swing.JFormattedTextField();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jFormattedTextField4 = new javax.swing.JFormattedTextField();
        jPanel14 = new javax.swing.JPanel();
        jTextField4 = new javax.swing.JTextField();
        jPanel56 = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jFormattedTextField16 = new javax.swing.JFormattedTextField();
        jComboBox11 = new javax.swing.JComboBox<>();
        jPanel17 = new javax.swing.JPanel();
        jFormattedTextField6 = new javax.swing.JFormattedTextField();
        jPanel36 = new javax.swing.JPanel();
        jFormattedTextField15 = new javax.swing.JFormattedTextField();
        jComboBox9 = new javax.swing.JComboBox<>();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jPanel57 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel58 = new javax.swing.JPanel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jPanel37 = new javax.swing.JPanel();
        jTextField3 = new javax.swing.JTextField();
        jComboBox10 = new javax.swing.JComboBox<>();
        jPanel21 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jTextField17 = new javax.swing.JTextField();
        jPanel23 = new javax.swing.JPanel();
        jFormattedTextField7 = new javax.swing.JFormattedTextField();
        jPanel38 = new javax.swing.JPanel();
        jTextField14 = new javax.swing.JTextField();
        jPanel24 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jFormattedTextField10 = new javax.swing.JFormattedTextField();
        jPanel26 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        jPanel35 = new javax.swing.JPanel();
        jTextField15 = new javax.swing.JTextField();
        jPanel32 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jFormattedTextField11 = new javax.swing.JFormattedTextField();
        jComboBox5 = new javax.swing.JComboBox<>();
        jPanel34 = new javax.swing.JPanel();
        jTextField6 = new javax.swing.JTextField();
        jPanel39 = new javax.swing.JPanel();
        jTextField16 = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jFormattedTextField12 = new javax.swing.JFormattedTextField();
        jComboBox6 = new javax.swing.JComboBox<>();
        jPanel11 = new javax.swing.JPanel();
        jPanel59 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel60 = new javax.swing.JPanel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jPanel40 = new javax.swing.JPanel();
        jPanel41 = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        jFormattedTextField13 = new javax.swing.JFormattedTextField();
        jComboBox7 = new javax.swing.JComboBox<>();
        jPanel43 = new javax.swing.JPanel();
        jTextField8 = new javax.swing.JTextField();
        jPanel44 = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jPanel46 = new javax.swing.JPanel();
        jFormattedTextField14 = new javax.swing.JFormattedTextField();
        jComboBox8 = new javax.swing.JComboBox<>();
        jPanel47 = new javax.swing.JPanel();
        jTextField9 = new javax.swing.JTextField();
        jPanel48 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(name);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setFocusCycleRoot(false);
        setFocusTraversalPolicyProvider(true);
        setLocation(new java.awt.Point(100, 100));
        setMaximumSize(new java.awt.Dimension(1200, 800));
        setMinimumSize(new java.awt.Dimension(500, 400));
        setPreferredSize(new java.awt.Dimension(1000, 750));
        setSize(new java.awt.Dimension(1000, 700));
        setType(java.awt.Window.Type.UTILITY);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setMaximumSize(new java.awt.Dimension(900, 100));
        jPanel1.setMinimumSize(new java.awt.Dimension(500, 50));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 150));
        jPanel1.setLayout(new java.awt.GridLayout(2, 1));

        jPanel30.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 102), new java.awt.Color(255, 255, 51), null, null));
        jPanel30.setLayout(new java.awt.GridLayout(2, 3, 1, 1));

        jCheckBox2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox2.setText("neu");
        jCheckBox2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel30.add(jCheckBox2);

        jCheckBox3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox3.setText("aktiv");
        jCheckBox3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel30.add(jCheckBox3);

        jCheckBox4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox4.setText("MH unterschrieben");
        jCheckBox4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel30.add(jCheckBox4);

        jCheckBox5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox5.setText("Unterschr. Kunde");
        jCheckBox5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel30.add(jCheckBox5);

        jCheckBox6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox6.setText("an Einkauf weitergeleitet");
        jCheckBox6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel30.add(jCheckBox6);

        jCheckBox7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox7.setText("Investition/Leasing");
        jPanel30.add(jCheckBox7);

        jPanel1.add(jPanel30);

        jPanel31.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel31.setMinimumSize(new java.awt.Dimension(109, 160));
        jPanel31.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DE", "EU", "Sonst", "unbekannt" }));
        jComboBox1.setToolTipText("");
        jComboBox1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Herkunft", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel31.add(jComboBox1);

        jFormattedTextField3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Menge", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFormattedTextField3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel31.add(jFormattedTextField3);

        jFormattedTextField2.setBackground(new java.awt.Color(255, 204, 204));
        jFormattedTextField2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wunschliefertermin", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField2.setPreferredSize(new java.awt.Dimension(200, 30));
        jFormattedTextField2.setMinimumSize(new java.awt.Dimension(150, 25));
        jFormattedTextField2.setToolTipText("Введите желаемую дату поставки (dd.MM.yy)");
        jFormattedTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField2ActionPerformed(evt);
            }
        });
        jFormattedTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField2KeyPressed(evt);
            }
        });
        jPanel31.add(jFormattedTextField2);

        jPanel1.add(jPanel31);

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setMaximumSize(new java.awt.Dimension(900, 80));
        jPanel4.setMinimumSize(new java.awt.Dimension(500, 50));
        jPanel4.setName(""); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(800, 70));

        jScrollPane1.setViewportBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(238, 70));

        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(10);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Beschreibung", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jTextArea1.setMaximumSize(new java.awt.Dimension(2000, 2000));
        jTextArea1.setMinimumSize(new java.awt.Dimension(232, 50));
        jTextArea1.setName(""); // NOI18N
        jTextArea1.setPreferredSize(new java.awt.Dimension(700, 70));
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel9.setAutoscrolls(true);
        jPanel9.setMaximumSize(new java.awt.Dimension(1000, 700));
        jPanel9.setMinimumSize(new java.awt.Dimension(600, 100));
        jPanel9.setPreferredSize(new java.awt.Dimension(900, 400));
        jPanel9.setRequestFocusEnabled(false);
        jPanel9.setVerifyInputWhenFocusTarget(false);
        jPanel9.setLayout(new java.awt.BorderLayout(1, 1));

        jScrollPane2.setViewportBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane2.setMaximumSize(new java.awt.Dimension(1200, 1000));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(600, 160));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(900, 300));
        jScrollPane2.setViewportView(jPanel2);

        jPanel2.setAutoscrolls(true);
        jPanel2.setFocusCycleRoot(true);
        jPanel2.setFocusTraversalPolicyProvider(true);
        jPanel2.setMaximumSize(new java.awt.Dimension(1200, 1000));
        jPanel2.setMinimumSize(new java.awt.Dimension(500, 300));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(600, 800));
        jPanel2.setLayout(new java.awt.GridLayout(12, 1, 1, 1));

        jPanel27.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel28.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 102), new java.awt.Color(0, 153, 102), null, null));
        jPanel28.setLayout(new java.awt.BorderLayout());

        tFAgreementNR.setText("jTextField11");
        tFAgreementNR.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Agreement Nr.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel28.add(tFAgreementNR, java.awt.BorderLayout.CENTER);

        jPanel27.add(jPanel28);

        jPanel29.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel29.setLayout(new javax.swing.BoxLayout(jPanel29, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Preis", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField17.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        jFormattedTextField17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jFormattedTextField17MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jFormattedTextField17MouseExited(evt);
            }
        });
        jFormattedTextField17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField17KeyPressed(evt);
            }
        });
        jPanel29.add(jFormattedTextField17);

        jComboBox12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Euro", "USD", "Others" }));
        jComboBox12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Währung", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel29.add(jComboBox12);

        jPanel27.add(jPanel29);

        jPanel50.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));

        jPanel49.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel49.setLayout(new javax.swing.BoxLayout(jPanel49, javax.swing.BoxLayout.LINE_AXIS));

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setText("RS");
        jButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 51), new java.awt.Color(0, 204, 102), null, null));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton1MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
        });
        jPanel49.add(jButton1);

        jPanel52.setLayout(new javax.swing.BoxLayout(jPanel52, javax.swing.BoxLayout.LINE_AXIS));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Rot", "Gelb", "Grün" }));
        jComboBox2.setSelectedIndex(1);
        jComboBox2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Status Anlieferung GR", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel52.add(jComboBox2);

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel52, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE))
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addComponent(jPanel52, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addComponent(jPanel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel27.add(jPanel50);

        jPanel2.add(jPanel27);

        jPanel5.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 102), new java.awt.Color(0, 153, 102), null, null));
        jPanel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datum der Anforderung", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField1.setToolTipText("Datum in Format dd.mm.yy");
        jFormattedTextField1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel6.add(jFormattedTextField1);

        jPanel5.add(jPanel6);

        jPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));

        tFKommentar01.setText("jTextField13");
        tFKommentar01.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lieferant", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel7.add(tFKommentar01);

        jPanel5.add(jPanel7);

        jPanel51.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel51.setLayout(new javax.swing.BoxLayout(jPanel51, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lieferung an GR erfolgt am", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField8.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField8KeyPressed(evt);
            }
        });
        jPanel51.add(jFormattedTextField8);

        jPanel5.add(jPanel51);

        jPanel2.add(jPanel5);

        jPanel3.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel53.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 102), new java.awt.Color(0, 153, 102), null, null));
        jPanel53.setLayout(new javax.swing.BoxLayout(jPanel53, javax.swing.BoxLayout.LINE_AXIS));

        tFAnforderungNR.setText("jTextField12");
        tFAnforderungNR.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Anforderung Nr.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel53.add(tFAnforderungNR);

        jPanel3.add(jPanel53);

        jPanel54.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bestellt am (dd.mm.yy)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel54.setLayout(new javax.swing.BoxLayout(jPanel54, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField5.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField5KeyPressed(evt);
            }
        });
        jPanel54.add(jFormattedTextField5);

        jPanel3.add(jPanel54);

        jPanel55.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel55.setLayout(new javax.swing.BoxLayout(jPanel55, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wareneingang erfolgt am", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField9.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField9KeyPressed(evt);
            }
        });
        jPanel55.add(jFormattedTextField9);

        jPanel3.add(jPanel55);

        jPanel2.add(jPanel3);

        jPanel12.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 102), new java.awt.Color(0, 153, 102), null, null));
        jPanel13.setLayout(new javax.swing.BoxLayout(jPanel13, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bestellt am (dd.mm.yy)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField4.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField4KeyPressed(evt);
            }
        });
        jPanel13.add(jFormattedTextField4);

        jPanel12.add(jPanel13);

        jPanel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel14.setLayout(new java.awt.BorderLayout());

        jTextField4.setText("jTextField4");
        jTextField4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bestellungsnummer", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel14.add(jTextField4, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel14);

        jPanel56.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel56.setLayout(new javax.swing.BoxLayout(jPanel56, javax.swing.BoxLayout.LINE_AXIS));

        jTextField5.setText("jTextField5");
        jTextField5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kommentar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel56.add(jTextField5);

        jPanel12.add(jPanel56);

        jPanel2.add(jPanel12);

        jPanel15.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel15.setLayout(new java.awt.GridLayout(1, 3));

        jPanel16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 102), new java.awt.Color(0, 153, 102), null, null));
        jPanel16.setLayout(new javax.swing.BoxLayout(jPanel16, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Summe für Material und Leistungen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField16.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        jFormattedTextField16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jFormattedTextField16MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jFormattedTextField16MouseExited(evt);
            }
        });
        jFormattedTextField16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField16KeyPressed(evt);
            }
        });
        jPanel16.add(jFormattedTextField16);

        jComboBox11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Euro", "USD", "Others" }));
        jComboBox11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Währung", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel16.add(jComboBox11);

        jPanel15.add(jPanel16);

        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel17.setLayout(new javax.swing.BoxLayout(jPanel17, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wareneingang TS nach Plan (dd.mm.yy)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField6.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField6KeyPressed(evt);
            }
        });
        jPanel17.add(jFormattedTextField6);

        jPanel15.add(jPanel17);

        jPanel36.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel36.setLayout(new javax.swing.BoxLayout(jPanel36, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Gewicht", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField15.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        jFormattedTextField15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField15KeyPressed(evt);
            }
        });
        jPanel36.add(jFormattedTextField15);

        jComboBox9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tonnen", "Kg", "" }));
        jComboBox9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Einh.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel36.add(jComboBox9);

        jPanel15.add(jPanel36);

        jPanel2.add(jPanel15);

        jPanel18.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel19.setBackground(new java.awt.Color(255, 255, 204));
        jPanel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, new java.awt.Color(204, 255, 204)));
        jPanel19.setLayout(new java.awt.BorderLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 204));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setLabelFor(jPanel19);
        jLabel1.setText("<Abrechnung mit Kunden>");
        jLabel1.setAlignmentX(0.5F);
        jLabel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 51, 0), java.awt.Color.gray, new java.awt.Color(255, 255, 51), new java.awt.Color(255, 255, 204)));
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setMinimumSize(new java.awt.Dimension(100, 10));
        jLabel1.setOpaque(true);
        jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel19.add(jLabel1, java.awt.BorderLayout.CENTER);

        jPanel18.add(jPanel19);

        jPanel20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));

        jPanel57.setLayout(new javax.swing.BoxLayout(jPanel57, javax.swing.BoxLayout.LINE_AXIS));

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setText("RS");
        jButton2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton2MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton2MousePressed(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel57.add(jButton2);

        jPanel58.setLayout(new javax.swing.BoxLayout(jPanel58, javax.swing.BoxLayout.LINE_AXIS));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Rot", "Gelb", "Grün" }));
        jComboBox3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Status Auslieferung an TS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel58.add(jComboBox3);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jPanel57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel58, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jPanel57, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel18.add(jPanel20);

        jPanel37.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel37.setLayout(new javax.swing.BoxLayout(jPanel37, javax.swing.BoxLayout.LINE_AXIS));

        jTextField3.setText("jTextField3");
        jTextField3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "L x B x H", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel37.add(jTextField3);

        jComboBox10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "CM", "MM" }));
        jComboBox10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Einh.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel37.add(jComboBox10);

        jPanel18.add(jPanel37);

        jPanel2.add(jPanel18);

        jPanel21.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel21.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel22.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, null));
        jPanel22.setLayout(new javax.swing.BoxLayout(jPanel22, javax.swing.BoxLayout.LINE_AXIS));

        jTextField17.setText("jTextField17");
        jTextField17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "RG-Nummer an AG", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel22.add(jTextField17);

        jPanel21.add(jPanel22);

        jPanel23.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel23.setLayout(new javax.swing.BoxLayout(jPanel23, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wareneingang TS erfolgt am", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField7.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField7KeyPressed(evt);
            }
        });
        jPanel23.add(jFormattedTextField7);

        jPanel21.add(jPanel23);

        jPanel38.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel38.setLayout(new javax.swing.BoxLayout(jPanel38, javax.swing.BoxLayout.LINE_AXIS));

        jTextField14.setText("jTextField14");
        jTextField14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kolli", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel38.add(jTextField14);

        jPanel21.add(jPanel38);

        jPanel2.add(jPanel21);

        jPanel24.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel25.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, null));
        jPanel25.setLayout(new javax.swing.BoxLayout(jPanel25, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datum", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField10.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField10KeyPressed(evt);
            }
        });
        jPanel25.add(jFormattedTextField10);

        jPanel24.add(jPanel25);

        jPanel26.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel26.setLayout(new javax.swing.BoxLayout(jPanel26, javax.swing.BoxLayout.LINE_AXIS));

        jTextField2.setText("jTextField2");
        jTextField2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wareneingang TS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel26.add(jTextField2);

        jPanel24.add(jPanel26);

        jPanel35.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel35.setLayout(new javax.swing.BoxLayout(jPanel35, javax.swing.BoxLayout.LINE_AXIS));

        jTextField15.setText("jTextField15");
        jTextField15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "PL", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel35.add(jTextField15);

        jPanel24.add(jPanel35);

        jPanel2.add(jPanel24);

        jPanel32.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel33.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, null));
        jPanel33.setLayout(new javax.swing.BoxLayout(jPanel33, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Weitergerechnet an AG", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField11.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        jFormattedTextField11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jFormattedTextField11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jFormattedTextField11MouseExited(evt);
            }
        });
        jFormattedTextField11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField11KeyPressed(evt);
            }
        });
        jPanel33.add(jFormattedTextField11);

        jComboBox5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Euro", "USD", "DK", "Other" }));
        jComboBox5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Währung", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel33.add(jComboBox5);

        jPanel32.add(jPanel33);

        jPanel34.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel34.setLayout(new javax.swing.BoxLayout(jPanel34, javax.swing.BoxLayout.LINE_AXIS));

        jTextField6.setText("jTextField6");
        jTextField6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Auslieferung nach Plan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel34.add(jTextField6);

        jPanel32.add(jPanel34);

        jPanel39.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel39.setLayout(new javax.swing.BoxLayout(jPanel39, javax.swing.BoxLayout.LINE_AXIS));

        jTextField16.setText("jTextField16");
        jTextField16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Container", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel39.add(jTextField16);

        jPanel32.add(jPanel39);

        jPanel2.add(jPanel32);

        jPanel8.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel10.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, null));
        jPanel10.setLayout(new javax.swing.BoxLayout(jPanel10, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Angebotssumme weitergerechnet", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField12.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        jFormattedTextField12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jFormattedTextField12MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jFormattedTextField12MouseExited(evt);
            }
        });
        jFormattedTextField12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField12KeyPressed(evt);
            }
        });
        jPanel10.add(jFormattedTextField12);

        jComboBox6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Euro", "USD", "DK", "Others" }));
        jComboBox6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Währung", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel10.add(jComboBox6);

        jPanel8.add(jPanel10);

        jPanel11.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));

        jPanel59.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel59.setLayout(new javax.swing.BoxLayout(jPanel59, javax.swing.BoxLayout.LINE_AXIS));

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setText("RS");
        jButton3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton3MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton3MousePressed(evt);
            }
        });
        jPanel59.add(jButton3);

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Rot", "Gelb", "Grün" }));
        jComboBox4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Status Auslieferung DNK", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        javax.swing.GroupLayout jPanel60Layout = new javax.swing.GroupLayout(jPanel60);
        jPanel60.setLayout(jPanel60Layout);
        jPanel60Layout.setHorizontalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 207, Short.MAX_VALUE)
            .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jComboBox4, javax.swing.GroupLayout.Alignment.TRAILING, 0, 261, Short.MAX_VALUE))
        );
        jPanel60Layout.setVerticalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel60Layout.createSequentialGroup()
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jPanel59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel59, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                    .addComponent(jPanel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );

        jPanel8.add(jPanel11);

        jPanel40.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel40.setLayout(new javax.swing.BoxLayout(jPanel40, javax.swing.BoxLayout.LINE_AXIS));
        jPanel8.add(jPanel40);

        jPanel2.add(jPanel8);

        jPanel41.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel42.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, null));
        jPanel42.setLayout(new javax.swing.BoxLayout(jPanel42, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Angebotssumme nicht weitergerechnet", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField13.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        jFormattedTextField13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jFormattedTextField13MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jFormattedTextField13MouseExited(evt);
            }
        });
        jFormattedTextField13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField13KeyPressed(evt);
            }
        });
        jPanel42.add(jFormattedTextField13);

        jComboBox7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Euro", "USD", "Others" }));
        jComboBox7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Währung", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel42.add(jComboBox7);

        jPanel41.add(jPanel42);

        jPanel43.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel43.setLayout(new javax.swing.BoxLayout(jPanel43, javax.swing.BoxLayout.LINE_AXIS));

        jTextField8.setText("jTextField8");
        jTextField8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Zahlungsbedingungen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel43.add(jTextField8);

        jPanel41.add(jPanel43);

        jPanel44.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel44.setLayout(new javax.swing.BoxLayout(jPanel44, javax.swing.BoxLayout.LINE_AXIS));
        jPanel41.add(jPanel44);

        jPanel2.add(jPanel41);

        jPanel45.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel46.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, null));
        jPanel46.setLayout(new javax.swing.BoxLayout(jPanel46, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kunden in Rechnung stellen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField14.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        jFormattedTextField14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jFormattedTextField14MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jFormattedTextField14MouseExited(evt);
            }
        });
        jFormattedTextField14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField14KeyPressed(evt);
            }
        });
        jPanel46.add(jFormattedTextField14);

        jComboBox8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Euro", "USD", "Others" }));
        jComboBox8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Währung", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel46.add(jComboBox8);

        jPanel45.add(jPanel46);

        jPanel47.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel47.setLayout(new javax.swing.BoxLayout(jPanel47, javax.swing.BoxLayout.LINE_AXIS));

        jTextField9.setText("jTextField9");
        jTextField9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kommentar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel47.add(jTextField9);

        jPanel45.add(jPanel47);

        jPanel48.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel48.setLayout(new javax.swing.BoxLayout(jPanel48, javax.swing.BoxLayout.LINE_AXIS));
        jPanel45.add(jPanel48);

        jPanel2.add(jPanel45);

        jScrollPane2.setViewportView(jPanel2);

        jPanel9.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jMenuBar1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jMenu1.setText("File");

        jMenuItem1.setText("Speichern in DB");
        jMenuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jMenuItem1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jMenuItem1MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem1MousePressed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jMenuItem2.setText("Währungen");
        jMenuItem2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jMenuItem2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jMenuItem2MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem2MousePressed(evt);
            }
        });
        jMenuItem2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenuItem2KeyPressed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void jButton1MouseEntered(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
    }                                     

    private void jButton1MouseExited(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
    }                                    

    /**
     * Обработчик нажатия кнопки 1 с улучшенной логикой
     */
    private void jButton1MousePressed(java.awt.event.MouseEvent evt) {                                      
        try {
            System.out.println("Кнопка 1 нажата - обновление цветов комбобоксов");
            
            // Обновление цветов всех комбобоксов статуса
            backgroundCombo(jComboBox2);
            backgroundCombo(jComboBox3);
            backgroundCombo(jComboBox4);
            
            // Установка цвета фона формы на основе первого комбобокса
            this.setBackground(jComboBox2.getBackground());
            
            // Дополнительная валидация дат после изменения статуса
            validateAllDateFields();
            
        } catch (Exception ex) {
            System.err.println("Ошибка при обработке нажатия кнопки 1: " + ex.getMessage());
            ex.printStackTrace();
        }
    }                                     

    private void jButton2MouseEntered(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
    }                                     

    private void jButton2MouseExited(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
    }                                    

    /**
     * Обработчик нажатия кнопки 2 с улучшенной логикой
     */
    private void jButton2MousePressed(java.awt.event.MouseEvent evt) {                                      
        try {
            System.out.println("Кнопка 2 нажата - обновление цветов комбобоксов");
            
            // Обновление цветов всех комбобоксов статуса
            backgroundCombo(jComboBox2);
            backgroundCombo(jComboBox3);
            backgroundCombo(jComboBox4);
            
            // Установка цвета фона формы на основе первого комбобокса
            this.setBackground(jComboBox2.getBackground());
            
            // Дополнительная валидация дат после изменения статуса
            validateAllDateFields();
            
        } catch (Exception ex) {
            System.err.println("Ошибка при обработке нажатия кнопки 2: " + ex.getMessage());
            ex.printStackTrace();
        }
    }                                     

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void jButton3MouseEntered(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
    }                                     

    private void jButton3MouseExited(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
    }                                    

    /**
     * Обработчик нажатия кнопки 3 с улучшенной логикой
     */
    private void jButton3MousePressed(java.awt.event.MouseEvent evt) {                                      
        try {
            System.out.println("Кнопка 3 нажата - обновление цветов комбобоксов");
            
            // Обновление цветов всех комбобоксов статуса
            backgroundCombo(jComboBox2);
            backgroundCombo(jComboBox3);
            backgroundCombo(jComboBox4);
            
            // Установка цвета фона формы на основе первого комбобокса
            this.setBackground(jComboBox2.getBackground());
            
            // Дополнительная валидация дат после изменения статуса
            validateAllDateFields();
            
        } catch (Exception ex) {
            System.err.println("Ошибка при обработке нажатия кнопки 3: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void jMenuItem1MouseEntered(java.awt.event.MouseEvent evt) {                                        
        // TODO add your handling code here:
    }                                       

    private void jMenuItem1MouseExited(java.awt.event.MouseEvent evt) {                                       
        // TODO add your handling code here:
    }                                      

    private void jMenuItem1MousePressed(java.awt.event.MouseEvent evt) {                                        
        // TODO add your handling code here:
    }                                       

    private void jMenuItem2MouseEntered(java.awt.event.MouseEvent evt) {                                        
        // TODO add your handling code here:
    }                                       

    private void jMenuItem2MouseExited(java.awt.event.MouseEvent evt) {                                       
        // TODO add your handling code here:
    }                                      

    private void jMenuItem2MousePressed(java.awt.event.MouseEvent evt) {                                        
        // TODO add your handling code here:
    }                                       

    private void jMenuItem2KeyPressed(java.awt.event.KeyEvent evt) {                                      
        // TODO add your handling code here:
    }                                     

    private void jFormattedTextField8KeyPressed(java.awt.event.KeyEvent evt) {                                                
        // TODO add your handling code here:
    }                                               

    private void jFormattedTextField9KeyPressed(java.awt.event.KeyEvent evt) {                                                
        // TODO add your handling code here:
    }                                               

    private void jFormattedTextField4KeyPressed(java.awt.event.KeyEvent evt) {                                                
        // TODO add your handling code here:
    }                                               

    private void jFormattedTextField5KeyPressed(java.awt.event.KeyEvent evt) {                                                
        // TODO add your handling code here:
    }                                               

    private void jFormattedTextField6KeyPressed(java.awt.event.KeyEvent evt) {                                                
        // TODO add your handling code here:
    }                                               

    private void jFormattedTextField15KeyPressed(java.awt.event.KeyEvent evt) {                                                 
        // TODO add your handling code here:
    }                                                

    private void jFormattedTextField7KeyPressed(java.awt.event.KeyEvent evt) {                                                
        // TODO add your handling code here:
    }                                               

    private void jFormattedTextField12KeyPressed(java.awt.event.KeyEvent evt) {                                                 
        // TODO add your handling code here:
    }                                                

    private void jFormattedTextField11KeyPressed(java.awt.event.KeyEvent evt) {                                                 
        // TODO add your handling code here:
    }                                                

    private void jFormattedTextField14KeyPressed(java.awt.event.KeyEvent evt) {                                                 
        // TODO add your handling code here:
    }                                                

    private void jFormattedTextField13KeyPressed(java.awt.event.KeyEvent evt) {                                                 
        // TODO add your handling code here:
    }                                                

    private void jFormattedTextField10KeyPressed(java.awt.event.KeyEvent evt) {                                                 
        // TODO add your handling code here:
    }                                                

    private void jFormattedTextField16KeyPressed(java.awt.event.KeyEvent evt) {                                                 
        // TODO add your handling code here:
    }                                                

    private void jFormattedTextField17KeyPressed(java.awt.event.KeyEvent evt) {                                                 
        // TODO add your handling code here:
    }                                                

    private void jFormattedTextField17MouseEntered(java.awt.event.MouseEvent evt) {                                                   
        // TODO add your handling code here:
    }                                                  

    private void jFormattedTextField17MouseExited(java.awt.event.MouseEvent evt) {                                                  
        // TODO add your handling code here:
    }                                                 

    private void jFormattedTextField16MouseEntered(java.awt.event.MouseEvent evt) {                                                   
        // TODO add your handling code here:
    }                                                  

    private void jFormattedTextField16MouseExited(java.awt.event.MouseEvent evt) {                                                  
        // TODO add your handling code here:
    }                                                 

    private void jFormattedTextField11MouseEntered(java.awt.event.MouseEvent evt) {                                                   
        // TODO add your handling code here:
    }                                                  

    private void jFormattedTextField12MouseEntered(java.awt.event.MouseEvent evt) {                                                   
        // TODO add your handling code here:
    }                                                  

    private void jFormattedTextField11MouseExited(java.awt.event.MouseEvent evt) {                                                  
        // TODO add your handling code here:
    }                                                 

    private void jFormattedTextField12MouseExited(java.awt.event.MouseEvent evt) {                                                  
        // TODO add your handling code here:
    }                                                 

    private void jFormattedTextField13MouseEntered(java.awt.event.MouseEvent evt) {                                                   
        // TODO add your handling code here:
    }                                                  

    private void jFormattedTextField13MouseExited(java.awt.event.MouseEvent evt) {                                                  
        // TODO add your handling code here:
    }                                                 

    private void jFormattedTextField14MouseEntered(java.awt.event.MouseEvent evt) {                                                   
        // TODO add your handling code here:
    }                                                  

    private void jFormattedTextField14MouseExited(java.awt.event.MouseEvent evt) {                                                  
        // TODO add your handling code here:
    }                                                 

    private void jFormattedTextField2KeyPressed(java.awt.event.KeyEvent evt) {                                                
       
        System.out.println( " KEY  :  "+ evt.getKeyCode());
       
        String str = jFormattedTextField2.getText();
       isDateOK(str);
       
    }                                               

    private void jFormattedTextField2ActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        // TODO add your handling code here:
    }                                                    

    private void formKeyPressed(java.awt.event.KeyEvent evt) {                                
      
           
       
    }                               

     public void keyPressed(Event e){
         System.out.println("KEY PRESSED  :" + e.key);
         
     }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BearbForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BearbForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BearbForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BearbForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               // BearbForm form =  new BearbForm();
              //  form.setVisible(true);
              
            }
        });
        
        
      
        
    }
     
    
    /**
     * Валидация всех полей дат в форме
     */
    private void validateAllDateFields() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
            sdf.setLenient(false);
            
            // Валидация всех полей дат
            validateDateField(jFormattedTextField1, sdf);
            validateDateField(jFormattedTextField2, sdf);
            validateDateField(jFormattedTextField4, sdf);
            validateDateField(jFormattedTextField5, sdf);
            validateDateField(jFormattedTextField6, sdf);
            validateDateField(jFormattedTextField7, sdf);
            validateDateField(jFormattedTextField8, sdf);
            validateDateField(jFormattedTextField9, sdf);
            validateDateField(jFormattedTextField10, sdf);
            
            System.out.println("Валидация всех полей дат завершена");
            
        } catch (Exception ex) {
            System.err.println("Ошибка при валидации полей дат: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    /**
     * Улучшенная установка цвета фона для комбобоксов
     * @param box комбобокс для изменения цвета
     */
    public void backgroundColorSet(JComboBox box){
        try {
            if (box == null) {
                System.err.println("Комбобокс не может быть null");
                return;
            }
            
            Object selectedItem = box.getSelectedItem();
            if (selectedItem == null) {
                box.setBackground(DEFAULT_COLOR);
                return;
            }
            
            String selectedText = selectedItem.toString();
            
            switch (selectedText) {
                case "Rot":
                    box.setBackground(Color.red);
                    box.setForeground(Color.WHITE);
                    break;
                case "Gelb":
                    box.setBackground(Color.yellow);
                    box.setForeground(Color.BLACK);
                    break;
                case "Grün":
                    box.setBackground(Color.green);
                    box.setForeground(Color.WHITE);
                    break;
                default:
                    box.setBackground(DEFAULT_COLOR);
                    box.setForeground(Color.BLACK);
                    break;
            }
            
        } catch (Exception ex) {
            System.err.println("Ошибка при установке цвета комбобокса: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox10;
    private javax.swing.JComboBox<String> jComboBox11;
    private javax.swing.JComboBox<String> jComboBox12;
    public javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JComboBox<String> jComboBox9;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField10;
    private javax.swing.JFormattedTextField jFormattedTextField11;
    private javax.swing.JFormattedTextField jFormattedTextField12;
    private javax.swing.JFormattedTextField jFormattedTextField13;
    private javax.swing.JFormattedTextField jFormattedTextField14;
    private javax.swing.JFormattedTextField jFormattedTextField15;
    private javax.swing.JFormattedTextField jFormattedTextField16;
    private javax.swing.JFormattedTextField jFormattedTextField17;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JFormattedTextField jFormattedTextField4;
    private javax.swing.JFormattedTextField jFormattedTextField5;
    private javax.swing.JFormattedTextField jFormattedTextField6;
    private javax.swing.JFormattedTextField jFormattedTextField7;
    private javax.swing.JFormattedTextField jFormattedTextField8;
    private javax.swing.JFormattedTextField jFormattedTextField9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextField tFAgreementNR;
    private javax.swing.JTextField tFAnforderungNR;
    private javax.swing.JTextField tFKommentar01;
    // End of variables declaration                   

    /**
     * Настройка размеров полей для лучшей видимости
     */
    private void setupFieldSizes() {
        try {
            // Настройка размеров панели jPanel31
            jPanel31.setMinimumSize(new java.awt.Dimension(300, 160));
            jPanel31.setPreferredSize(new java.awt.Dimension(900, 160));
            
            // Настройка размеров полей
            jComboBox1.setPreferredSize(new java.awt.Dimension(200, 30));
            jComboBox1.setMinimumSize(new java.awt.Dimension(150, 25));
            jComboBox1.setFont(new java.awt.Font("Segoe UI", 1, 11));
            jComboBox1.setToolTipText("Выберите страну происхождения");
            
            jFormattedTextField3.setPreferredSize(new java.awt.Dimension(200, 30));
            jFormattedTextField3.setMinimumSize(new java.awt.Dimension(150, 25));
            jFormattedTextField3.setFont(new java.awt.Font("Segoe UI", 1, 11));
            jFormattedTextField3.setToolTipText("Введите количество");
            
            jFormattedTextField2.setPreferredSize(new java.awt.Dimension(200, 30));
            jFormattedTextField2.setMinimumSize(new java.awt.Dimension(150, 25));
            jFormattedTextField2.setFont(new java.awt.Font("Segoe UI", 1, 11));
            jFormattedTextField2.setToolTipText("Введите желаемую дату поставки (dd.MM.yy)");
            
            System.out.println("Размеры полей настроены");
            
        } catch (Exception ex) {
            System.err.println("Ошибка при настройке размеров полей: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
