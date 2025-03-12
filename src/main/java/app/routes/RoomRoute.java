package app.routes;

public class RoomRoute {
    /*path("room", () -> {
                    post("/", (ctx) -> {
                        RoomDTO roomDTO = ctx.bodyAsClass(RoomDTO.class);
                        roomController.createRoom(roomDTO);
                        ctx.status(201).json(roomDTO);
                    });
                    get("/", (ctx) -> {
                        ctx.json(roomController.getRoomsForHotel();
                    });
                    post("/", (ctx) -> {
                        RoomDTO newRoom = ctx.bodyAsClass(RoomDTO.class);
                        RoomDTO returnedRoom = roomDAO.save(newRoom);
                        ctx.json(returnedRoom);
                    });
                    put("/", (ctx) -> {
                        try {
                            RoomDTO room = ctx.bodyAsClass(RoomDTO.class);
                            RoomDTO updatedRoom = roomDAO.updateById(Integer.parseInt(ctx.pathParam("id")), room);
                            ctx.json(updatedRoom);
                        } catch (Exception e) {
                            ctx.status(404).result("Room not found");
                        }
                    });
                    delete("/:id", (ctx) -> {
                        int id = Integer.parseInt(ctx.pathParam("id"));
                        boolean deleted = roomDAO.deleteById(id);
                        if (deleted) {
                            ctx.status(204);
                        } else {
                            ctx.status(404).result("Room not found");
                        }
                    });
                });*/
}
