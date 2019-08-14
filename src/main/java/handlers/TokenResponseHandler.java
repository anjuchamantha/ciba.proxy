package handlers;

import cibaparameters.CIBAParameters;
import com.nimbusds.jose.*;
import com.nimbusds.jwt.JWT;

import dao.DaoFactory;
import dao.DbConnectors;
import errorfiles.Forbidden;
import com.nimbusds.jwt.JWTClaimsSet;
import transactionartifacts.TokenResponse;

import java.util.logging.Logger;


/**
 * This class provides the token reponses once validated.
 * */
public class TokenResponseHandler implements Handlers {
    private static final Logger LOGGER = Logger.getLogger(TokenResponseHandler.class.getName());
    private TokenResponseHandler() {

    }

    private static TokenResponseHandler tokenResponseHandlerInstance = new TokenResponseHandler();

    public static TokenResponseHandler getInstance() {
        if (tokenResponseHandlerInstance == null) {

            synchronized (TokenResponseHandler.class) {

                if (tokenResponseHandlerInstance == null) {

                    /* instance will be created at request time */
                    tokenResponseHandlerInstance = new TokenResponseHandler();
                }
            }
        }
        return tokenResponseHandlerInstance;


    }


    public Payload createTokenResponse(String auth_req_id) {
        CIBAParameters cibaparameters = CIBAParameters.getInstance();
        TokenResponse tokenResponse = DaoFactory.getInstance().getConnector("InMemoryCache").getTokenResponse(auth_req_id);



        // String clientNotificationToken = cibaparameters.getClient_notification_token();


        //BearerAccessToken bearerAccessToken = new BearerAccessToken(clientNotificationToken);
        //creating bearer access token and adding to header
        //JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).customParam("Authorization", bearerAccessToken).build();
        // TODO: 8/7/19 Should concider ping flow also

        //Only checking the presence of refresh token and creating payload accordingly
        if (tokenResponse.getRefreshToken() != null) {
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .claim("access_token", tokenResponse.getAccessToken())
                    .claim("token_type", tokenResponse.getTokenType())
                    .claim("refresh_token", tokenResponse.getRefreshToken())
                    .claim("token_expires_in", tokenResponse.getTokenExpirein())
                    .claim("id_token", tokenResponse.getIdToken())
                    .build();


            Payload payload = new Payload(claims.toJSONObject());
            //JWSObject response = new JWSObject(header, payload);
            return payload;
        } else {
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .claim("access_token", tokenResponse.getAccessToken())
                    .claim("token_type", tokenResponse.getTokenType())
                    .claim("token_expires_in", tokenResponse.getTokenExpirein())
                    .claim("id_token", tokenResponse.getIdToken())
                    .build();


            Payload payload = new Payload(claims.toJSONObject());
            //JWSObject response = new JWSObject(header, payload);
            return payload;
        }
    }

       public Payload createTokenErrorResponse () {

            ErrorCodeHandlers errorCodeHandlers = ErrorCodeHandlers.getInstance();
            try {
                throw new Forbidden("Invalid Token Request");
            } catch (Forbidden forbidden) {
                forbidden.printStackTrace();
            }

            return new Payload("Invalid");
        }

        public boolean checkTokenReceived (String auth_req_id) {

            if (DaoFactory.getInstance().getConnector("InMemoryCache").getTokenResponse(auth_req_id) != null) {
                return true;
            }
            LOGGER.info("Token Response still not received");
            return false;
        }

}