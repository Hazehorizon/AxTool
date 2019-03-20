package com.hazehorizon.ax.testconnector.stackoverflow.server;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("2.2/tags")
@Produces(MediaType.APPLICATION_JSON)
public class TagsService extends AbstractService {
}