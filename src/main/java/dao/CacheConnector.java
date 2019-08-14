package dao;

import cache.CibaProxyCache;
import handlers.Handlers;
import transactionartifacts.CIBAauthRequest;
import transactionartifacts.CIBAauthResponse;
import transactionartifacts.TokenRequest;
import transactionartifacts.TokenResponse;

/**
 * This class supports Cache -in memory storage.
 * */
public class CacheConnector  implements DbConnectors {

    CibaProxyCache cibaProxyCache;

    private CacheConnector() {
        cibaProxyCache = CibaProxyCache.getCibaProxyCacheInstance();
    }

    private static CacheConnector cacheConnectorInstance = new CacheConnector();

    public static CacheConnector getInstance() {
        if (cacheConnectorInstance == null) {

            synchronized (CacheConnector.class) {

                if (cacheConnectorInstance == null) {

                    /* instance will be created at request time */
                    cacheConnectorInstance = new CacheConnector();
                }
            }
        }
        return cacheConnectorInstance;
    }





    /**
     * Add Authentication request object to authrequest cache.
     * */
    public void addAuthRequest(String auth_req_id, Object authrequest) {
        if (authrequest instanceof CIBAauthRequest) {
            cibaProxyCache.getAuthRequestCache().add(auth_req_id, authrequest);
        } else {
            // TODO: 8/6/19 do logging here for all below
        }
    }


    /**
     * Add Authentication response object to authresponse cache.
     * */
    public void addAuthResponse(String auth_req_id, Object authresponse) {
        if (authresponse instanceof CIBAauthResponse) {
            cibaProxyCache.getAuthResponseCache().add(auth_req_id, authresponse);
        }
    }


    /**
     * Add Token request object to tokenrequest cache.
     * */
    public void addTokenRequest(String auth_req_id, Object tokenrequest) {
        if (tokenrequest instanceof TokenRequest) {
            cibaProxyCache.getTokenRequestCache().add(auth_req_id, tokenrequest);
        }
    }


    /**
     * Add Authentication response object to token cache.
     * */
    public void addTokenResponse(String auth_req_id, Object tokenresponse) {
        if (tokenresponse instanceof TokenResponse) {
            cibaProxyCache.getTokenResponseCache().add(auth_req_id, tokenresponse);
        }
    }


    /**
     * Add expiry time of each token object to expiry cache.
     * */
    public void addExpiresTime(String auth_req_id, long timestamp) {
        cibaProxyCache.getExpiresInCache().add(auth_req_id,timestamp);
    }


    /**
     * Add latest polling time to last-poll cache.
     * */
    public void addLastPollTime(String auth_req_id, long lastpolltime) {
        cibaProxyCache.getLastPollCache().add(auth_req_id, lastpolltime);
    }


    /**
     * Add issuedtime  to issuedtime cache.
     * */
    public void addIssuedTime(String auth_req_id, long issuedtime) {
        cibaProxyCache.getIssuedTimeCache().add(auth_req_id, issuedtime);
    }


    /**
     * Add interval to interval cache.
     * */
    public void addInterval(String auth_req_id, long interval) {
        cibaProxyCache.getIntervalCache().add(auth_req_id, interval);
    }


    /**
     * Remove Authentication request object from authrequest cache.
     * */
    public void removeAuthRequest(String auth_req_id) {
        cibaProxyCache.getAuthRequestCache().remove(auth_req_id);
    }

    /**
     * Remove Authentication response object from authresponse cache.
     * */
    public void removeAuthResponse(String auth_req_id) {
        cibaProxyCache.getAuthResponseCache().remove(auth_req_id);
    }

    /**
     * Remove token request object from tokenrequest cache.
     * */
    public void removeTokenRequest(String auth_req_id) {
        cibaProxyCache.getTokenRequestCache().remove(auth_req_id);
    }

    /**
     * Remove Authentication response object from authresponse cache.
     * */
    public void removeTokenResponse(String auth_req_id) {
        cibaProxyCache.getTokenResponseCache().remove(auth_req_id);
    }

    /**
     * Remove expiry time from expiry cache.
     * */
    public void removeExpiresTime(String auth_req_id) {
        cibaProxyCache.getExpiresInCache().remove(auth_req_id);
    }

    /**
     * Remove last polling time from last-poll cache.
     * */
    public void removeLastPollTime(String auth_req_id) {
        cibaProxyCache.getLastPollCache().remove(auth_req_id);
    }

    /**
     * Remove last issued time from issuedtime cache.
     * */
    public void removeIssuedTime(String auth_req_id) {
        cibaProxyCache.getIssuedTimeCache().remove(auth_req_id);
    }

    /**
     * Remove interval from interval cache.
     * */
    public void removeInterval(String auth_req_id) {
        cibaProxyCache.getIntervalCache().remove(auth_req_id);
    }


    /**
     * Get CIBA auth request  from Authrequest cache.
     * */
    public CIBAauthRequest getAuthRequest(String auth_req_id) {
       return (CIBAauthRequest) cibaProxyCache.getAuthRequestCache().get(auth_req_id);
    }


    /**
     * Get CIBA auth response  from Authresponse cache.
     * */
    public CIBAauthResponse getAuthResponse(String auth_req_id) {
        return CIBAauthResponse.class.cast(cibaProxyCache.getAuthResponseCache().get(auth_req_id));
    }


    /**
     * Get Token request  from token request cache.
     * */
    public TokenRequest getTokenRequest(String auth_req_id) {
        return TokenRequest.class.cast(cibaProxyCache.getTokenRequestCache().get(auth_req_id));
    }


    /**
     * Get token response  from token response cache.
     * */
    public TokenResponse getTokenResponse(String auth_req_id) {
        return TokenResponse.class.cast(cibaProxyCache.getTokenResponseCache().get(auth_req_id));
    }


    /**
     * Get expiry from expirytime cache.
     * */
    public long getExpiresTime(String auth_req_id) {
        return Long.parseLong(String.valueOf(cibaProxyCache.getExpiresInCache().get(auth_req_id)));
    }

    /**
     * Get lastpolltime from last-poll cache.
     * */
    public long getLastPollTime(String auth_req_id) {
        return Long.parseLong(String.valueOf(cibaProxyCache.getLastPollCache().get(auth_req_id)));
    }

    /**
     * Get issuedtime from issuedtime cache.
     * */
    public long getIssuedTime(String auth_req_id) {
        return Long.parseLong(String.valueOf((cibaProxyCache.getIssuedTimeCache().get(auth_req_id))));
    }

    /**
     * Get interval from interval cache.
     * */
    public long  getInterval(String auth_req_id) {
        return Long.parseLong(String.valueOf((cibaProxyCache.getIntervalCache().get(auth_req_id))));

    }

    @Override
    public void registerToAuthRequestCache(Object authRequestHandler) {
        cibaProxyCache.getAuthRequestCache().register(authRequestHandler);

    }

    @Override
    public void registerToAuthResponseCache(Object authResponseHandler) {
        cibaProxyCache.getAuthResponseCache().register(authResponseHandler);

    }

    @Override
    public void registerToTokenResponseCache(Object tokenRequestHandler) {
        cibaProxyCache.getTokenRequestCache().register(tokenRequestHandler);

    }

    @Override
    public void registerToTokenRequestCache(Object tokenResponseHandler) {

    }


    @Override
    public void registerToExpiryTimeCache(Object expiryHandler) {
        cibaProxyCache.getExpiresInCache().register(expiryHandler);
    }

    @Override
    public void registerToLastPollCache(Object pollHandler) {
        cibaProxyCache.getLastPollCache().register(pollHandler);
    }

    @Override
    public void registerToIssuedTimeCache(Object issuedTimeHandler) {
        cibaProxyCache.getIssuedTimeCache().register(issuedTimeHandler);
    }

    @Override
    public void registerToIntervalCache(Object intervalHandler) {
        cibaProxyCache.getIntervalCache().register(intervalHandler);
    }
}