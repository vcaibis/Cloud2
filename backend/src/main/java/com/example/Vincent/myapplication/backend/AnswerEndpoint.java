package com.example.Vincent.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "answerApi",
        version = "v1",
        resource = "answer",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Vincent.example.com",
                ownerName = "backend.myapplication.Vincent.example.com",
                packagePath = ""
        )
)
public class AnswerEndpoint {

    private static final Logger logger = Logger.getLogger(AnswerEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Answer.class);
    }

    /**
     * Returns the {@link Answer} with the corresponding ID.
     *
     * @param Id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Answer} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "answer/{Id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Answer get(@Named("Id") Long Id) throws NotFoundException {
        logger.info("Getting Answer with ID: " + Id);
        Answer answer = ofy().load().type(Answer.class).id(Id).now();
        if (answer == null) {
            throw new NotFoundException("Could not find Answer with ID: " + Id);
        }
        return answer;
    }

    /**
     * Inserts a new {@code Answer}.
     */
    @ApiMethod(
            name = "insert",
            path = "answer",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Answer insert(Answer answer) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that answer.Id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(answer).now();
        logger.info("Created Answer with ID: " + answer.getId());

        return ofy().load().entity(answer).now();
    }

    /**
     * Updates an existing {@code Answer}.
     *
     * @param Id     the ID of the entity to be updated
     * @param answer the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code Id} does not correspond to an existing
     *                           {@code Answer}
     */
    @ApiMethod(
            name = "update",
            path = "answer/{Id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Answer update(@Named("Id") Long Id, Answer answer) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(Id);
        ofy().save().entity(answer).now();
        logger.info("Updated Answer: " + answer);
        return ofy().load().entity(answer).now();
    }

    /**
     * Deletes the specified {@code Answer}.
     *
     * @param Id the ID of the entity to delete
     * @throws NotFoundException if the {@code Id} does not correspond to an existing
     *                           {@code Answer}
     */
    @ApiMethod(
            name = "remove",
            path = "answer/{Id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("Id") Long Id) throws NotFoundException {
        checkExists(Id);
        ofy().delete().type(Answer.class).id(Id).now();
        logger.info("Deleted Answer with ID: " + Id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "answer",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Answer> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Answer> query = ofy().load().type(Answer.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Answer> queryIterator = query.iterator();
        List<Answer> answerList = new ArrayList<Answer>(limit);
        while (queryIterator.hasNext()) {
            answerList.add(queryIterator.next());
        }
        return CollectionResponse.<Answer>builder().setItems(answerList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long Id) throws NotFoundException {
        try {
            ofy().load().type(Answer.class).id(Id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Answer with ID: " + Id);
        }
    }
}