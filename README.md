# DataBaseImages
*PostgreSQL + hibernate + tomcat*

### Создать собственный сервлет на сервере и настроить свою базу данных.

Сделать 3 запроса:

**1)** Отправить картинку на сервер в формате JSON:

   1.1) Описание картинки, например "пейзаж".

   1.2) Сама картинка в формате base64.

   1.3) Сервер должен вернуть ID картинки и сохранить в базу. В оперативной памяти не храним.

**2)** Получить список всех картинок в формате JSON, без самих картинок, только описание и ID.

**3)** Получить картинку по ID по прямой ссылке.

#### =======================================================================

На сервере настроенна база данных с таблицей под названием *statyva*, в которой есть следующие поля:

- image_id для идентификации
- image_name для описания 
- image_base для хранения картинки в формате base64

#### 1. Отправить картинку на сервер в формате JSON (POST запрос)

POST запрос:
(ID генерируется автоматически)

```
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Response addImage(@FormParam("name") String name, @FormParam("base") String base) {
        Image image = new Image(name, base);
        String json = gson.toJson(imageDao.addImage(image));
        return Response
                .status(Response.Status.OK)
                .entity("Image added with ID: " + json)
                .build();
    }
```

При успешном выполнении возвращает: *Image added with ID: №*

#### 2. Получить список всех картинок в формате JSON, без самих картинок, только описание и ID (GET запрос)

GET запрос:

```
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getAllImages() {
        List images = imageDao.getAllImages();
        String json = gson.toJson(images);
        return Response
                .status(Response.Status.OK)
                .entity(json)
                .build();
    }
```

Получаем ответ в формате JSON полный список ID (image_id) и описание(image_name) хранящиеся в ячейке картинки:

```
[
  [
    1,
    "JavaLogo"
  ],
  [
    2,
    "PixelCat"
  ],
  [
    3,
    "Nature"
  ],
  [
    4,
    "TheHorde"
  ]
]
```

**GET JSON:** ***http://164.68.101.149:12345/statyva/api***

#### 3. Получить картинку по ID по прямой ссылке (POST запрос)

POST запрос:

```
    @POST
    @Path("/image")
    @Produces("image/jpg")
    public Response getImage(@FormParam("id") long id) {
        byte[] arr;
        try {
            arr = Base64.getDecoder().decode(imageDao.getImage(id).toString());

            if (id == 0) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .build();
            }
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        } return Response.ok(new ByteArrayInputStream(arr)).build();
    }
```

При отправке номера ID, если в базе данных есть такой ID, то выдает картинку соответствующей ID ячейки.
Если в базе данных нет такого ID, то выдаст 400 ошибку (BAD_REQUEST).
