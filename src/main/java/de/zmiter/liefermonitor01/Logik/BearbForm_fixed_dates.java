// Исправления для форматирования дат в BearbForm.java
// Заменить все вызовы .toString() на formatDate() для Date полей

// 1. Добавить импорт:
// import java.text.SimpleDateFormat;

// 2. Добавить метод форматирования:
// private String formatDate(Date date) {
//     if (date == null) {
//         return "";
//     }
//     SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
//     return sdf.format(date);
// }

// 3. Заменить все вызовы .toString() на formatDate():

// Строка 64: getDatumBS()
// this.jFormattedTextField1.setText(formatDate(lief.getDatumBS()));

// Строка 79: getWareneingangTS_Fakt()
// this.jFormattedTextField8.setText(formatDate(lief.getWareneingangTS_Fakt()));

// Строка 90: getWareneingangTS_plan()
// this.jFormattedTextField16.setText(formatDate(lief.getWareneingangTS_plan()));

// Строка 100: getWareneingangTS_Fakt()
// this.jFormattedTextField7.setText(formatDate(lief.getWareneingangTS_Fakt()));

// Строка 111: getAuslieferungNachPlan()
// this.jFormattedTextField4.setText(formatDate(lief.getAuslieferungNachPlan()));

// Строка 124: getWareneingangTS_Fakt()
// this.jFormattedTextField8.setText(formatDate(lief.getWareneingangTS_Fakt()));

// Строка 133: getWunschterminWareneingang()
// this.jFormattedTextField9.setText(formatDate(lief.getWunschterminWareneingang()));

// Строка 155: getDatumBS()
// this.jFormattedTextField10.setText(formatDate(lief.getDatumBS()));

// После этих изменений даты будут отображаться в формате dd.MM.yyyy
// вместо стандартного формата Java Date
