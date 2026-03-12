package dev.sergevas.iot.growlabv1.renderer.control;

import dev.sergevas.iot.growlabv1.renderer.control.entity.SensorReadings;

import static j2html.TagCreator.*;

public class HtmlService {

    public String generateHTML(SensorReadings sensorReadings) {
        return html(document(), head(
                        meta()
                                .withCharset("UTF-8"),
                        meta()
                                .withName("viewport")
                                .withContent("width=device-width, initial-scale=1.0"),
                        link()
                                .withHref("https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css")
                                .withRel("stylesheet"),
                        style().withText("body{background-color: rgb(16, 185, 129);}.upper{text-transform: uppercase;"),
                        title("Луковка онлайн")
                ),
                body(
                        nav(
                                div(
                                        a("Луковка онлайн")
                                                .withHref("#")
                                                .withClass("navbar-brand upper"),
                                        img().withSrc("onion.jpg").withWidth("60px")
                                                .withClass("navbar-brand mb-1")
                                ).withClass("container-fluid")
                        ).withClass("navbar navbar-dark bg-dark"),
                        div(div().withClass("p-2")).withClass("d-grid gap-3"),
                        div(
                                div(
                                        h5(blockquote("Добро пожаловать на школьный проект Егора!"))
                                                .withClass("card-header"),
                                        div(h5().withText("Луковка - это школьный проект по предмету [TODO]. Тема: [TODO]. Здесь можно наблюдать за ростом лука в реальном времени.")
                                                        .withClass("card-title"),
                                                h5().withText("Актуальные значения параметров окружающей среды:"),
                                                div(
                                                        table().withClass("table").with(
                                                                thead().with(
                                                                        tr().with(
                                                                                th("Параметр"),
                                                                                th("Значение")
                                                                        )
                                                                ),
                                                                tbody().with(
                                                                        tr().with(
                                                                                td("Дата и время"),
                                                                                td(sensorReadings.getMoscowTime())
                                                                        ),
                                                                        tr().with(
                                                                                td("Температура"),
                                                                                td(String.format("%s°C", sensorReadings.getTemperature()))
                                                                        ),
                                                                        tr().with(
                                                                                td("Относительная влажность"),
                                                                                td(String.format("%s%%", sensorReadings.getHumidity()))
                                                                        ),
                                                                        tr().with(
                                                                                td("Давление"),
                                                                                td(String.format("%s Па", sensorReadings.getPressure()))
                                                                        ),
                                                                        tr().with(
                                                                                td("Количество света"),
                                                                                td(String.format("%s лк", sensorReadings.getLight()))
                                                                        )
                                                                )
                                                        )
                                                )
                                        ).withClass("col-sm-6 col-lg-6 col-md-6 col-xl-6"),
                                        h5().withText("Свежее фото"),
                                        div(
                                                img().withSrc("response_mod.jpg").withClass("figure-img img-fluid rounded")
                                        ).withStyle("width: 85%;")
                                ).withClass("card mb-3")
                        ).withClass("container bg-light p-4 rounded"),
                        script().withSrc("https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js")
                )).attr("lang", "ru")
                .render();
    }
}
