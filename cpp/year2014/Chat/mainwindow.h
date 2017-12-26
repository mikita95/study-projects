#ifndef MAINWINDOW_H
#define MAINWINDOW_H
#include <QMainWindow>
#include <QUdpSocket>
#include <QTextLayout>

#include "ui_mainwindow.h"

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow, public Ui::MainWindow {
    Q_OBJECT
    public:
        MainWindow(QWidget *parent = 0);
    private slots:
        void on_loginButton_clicked();
        void on_sendButton_clicked();
        void readyRead();
        void itemDoubleClick(QListWidgetItem*);
    private:
        QUdpSocket *socket;
        QString name;
};
#endif // MAINWINDOW_H
