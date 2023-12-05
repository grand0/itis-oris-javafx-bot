module ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot to javafx.fxml;
    exports ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot;
    exports ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.game;
    exports ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.game.ui;
}
