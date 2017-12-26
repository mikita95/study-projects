#include "mainwindow.h"

#include <QRegExp>

MainWindow::MainWindow(QWidget *parent) : QMainWindow(parent) {
    setupUi(this);
    stackedWidget->setCurrentWidget(loginPage);
    socket = new QUdpSocket(this);
    socket->bind(QHostAddress::Any, 5678, QUdpSocket::ReuseAddressHint);
    connect(socket, SIGNAL(readyRead()), this, SLOT(readyRead()));
    connect(this->userListWidget, SIGNAL(itemDoubleClicked(QListWidgetItem*)), this, SLOT(itemDoubleClick(QListWidgetItem*)));
}

void MainWindow::itemDoubleClick(QListWidgetItem *item) {
    sayLineEdit->clear();
    sayLineEdit->setFocus();
    sayLineEdit->setText(item->text() + ", ");
}

void MainWindow::on_loginButton_clicked() {
    name = userLineEdit->text();
    QByteArray datagram;
    datagram.append(userLineEdit->text() + ":" + userLineEdit->text() + " connected\n");
    datagram.append("\n");
    socket->writeDatagram(datagram.data(), datagram.size(), QHostAddress::Broadcast, 5678);
    stackedWidget->setCurrentWidget(chatPage);
    return;
}

void MainWindow::on_sendButton_clicked() {
    QString message = sayLineEdit->text().trimmed();
    if(!message.isEmpty()) {
        QByteArray datagram;
        datagram.append(name + ":" + QString(message + "\n"));
        datagram.append("\n");
        socket->writeDatagram(datagram.data(), datagram.size(), QHostAddress::Broadcast, 5678);
    }
    sayLineEdit->clear();
    sayLineEdit->setFocus();
}


void MainWindow::readyRead() {
    while(socket->hasPendingDatagrams()) {
        QByteArray buffer;
        buffer.resize(socket->pendingDatagramSize());
        QHostAddress sender;
        quint16 port;
        socket->readDatagram(buffer.data(), buffer.size(), &sender, &port);
        QString line = QString::fromUtf8(buffer).trimmed();
        QRegExp messageRegex("^([^:]+):(.*)$");
        if (messageRegex.indexIn(line) != -1) {
            QString user = messageRegex.cap(1);
            QString message = messageRegex.cap(2);
            bool flag = false;
            for (int i = 0; i < userListWidget->count(); i++)
                if (userListWidget->item(i)->text() == user) {
                    flag = true;
                    break;
                }
            if (!flag)
                new QListWidgetItem(QPixmap(":/user.png"), user, userListWidget );
            roomTextEdit->append("<b>" + user + "</b>: " + message);
        }
    }
}

