package com.mkyong.chunk;

/**
 * User: wakkir
 * Date: 11/03/14
 * Time: 23:35
 */

/**
 * Interface for a remote worker in the Remote Chunking pattern. A request comes from a master process containing some
 * items to be processed. Once the items are done with a response needs to be generated containing a summary of the
 * result.
 *
 * @param <T> the type of the items to be processed (it is recommended to use a Memento like a primary key)
 * @author Dave Syer
 */
public interface ChunkHandler<T>
{

    /**
     * Handle the chunk, processing all the items and returning a response summarising the result. If the result is a
     * failure then the response should say so. The handler only throws an exception if it needs to roll back a
     * transaction and knows that the request will be re-delivered (if not to the same handler then to one processing
     * the same Step).
     *
     * @param chunk a request containing the chunk to process
     * @return a response summarising the result
     * @throws Exception if the handler needs to roll back a transaction and have the chunk re-delivered
     */
    ChunkResponse handleChunk(ChunkRequest<T> chunk) throws Exception;

}