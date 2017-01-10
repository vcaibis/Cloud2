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
        name = "questionApi",
        version = "v1",
        resource = "question",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Vincent.example.com",
                ownerName = "backend.myapplication.Vincent.example.com",
                packagePath = ""
        )
)
public class QuestionEndpoint {

    private static final Logger logger = Logger.getLogger(QuestionEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Question.class);
    }

    /**
     * Returns the {@link Question} with the corresponding ID.
     *
     * @param Id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Question} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "question/{Id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Question get(@Named("Id") Long Id) throws NotFoundException {
        logger.info("Getting Question with ID: " + Id);
        Question question = ofy().load().type(Question.class).id(Id).now();
        if (question == null) {
            throw new NotFoundException("Could not find Question with ID: " + Id);
        }
        return question;
    }

    /**
     * Inserts a new {@code Question}.
     */
    @ApiMethod(
            name = "insert",
            path = "question",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Question insert(Question question) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that question.Id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(question).now();
        logger.info("Created Question with ID: " + question.getId());

        return ofy().load().entity(question).now();
    }

    /**
     * Updates an existing {@code Question}.
     *
     * @param Id       the ID of the entity to be updated
     * @param question the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code Id} does not correspond to an existing
     *                           {@code Question}
     */
    @ApiMethod(
            name = "update",
            path = "question/{Id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Question update(@Named("Id") Long Id, Question question) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(Id);
        ofy().save().entity(question).now();
        logger.info("Updated Question: " + question);
        return ofy().load().entity(question).now();
    }

    /**
     * Deletes the specified {@code Question}.
     *
     * @param Id the ID of the entity to delete
     * @throws NotFoundException if the {@code Id} does not correspond to an existing
     *                           {@code Question}
     */
    @ApiMethod(
            name = "remove",
            path = "question/{Id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("Id") Long Id) throws NotFoundException {
        checkExists(Id);
        ofy().delete().type(Question.class).id(Id).now();
        logger.info("Deleted Question with ID: " + Id);
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
            path = "question",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Question> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Question> query = ofy().load().type(Question.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Question> queryIterator = query.iterator();
        List<Question> questionList = new ArrayList<Question>(limit);
        while (queryIterator.hasNext()) {
            questionList.add(queryIterator.next());
        }
        return CollectionResponse.<Question>builder().setItems(questionList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long Id) throws NotFoundException {
        try {
            ofy().load().type(Question.class).id(Id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Question with ID: " + Id);
        }
    }
}