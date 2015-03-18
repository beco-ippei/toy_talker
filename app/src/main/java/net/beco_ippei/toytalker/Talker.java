package net.beco_ippei.toytalker;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by ippei on 15/03/19.
 */
public class Talker
{
    private String ctx;

    Talker()
    {
        this.ctx = "context";       //TODO: とりあえず
    }

    private int getElapsedMinutes(Date from)
    {
        long diff = new Date().getTime() - from.getTime();
        return parseInt(diff / (60 * 1000), 10);
    }

    public void talk(String msg, String nickname)
    {
        HashMap params = new HashMap();
        params.put('utt', msg);
        params.put('nickname', nickname);
        params.put('context', this.ctx);

                params =
                        utt: msg
        nickname: nickname
        context: ctx
        robot.http(DOCOMO_AI_URL).post(JSON.stringify params) (err, r, body)->
        try
        if err
        console.log "   ... error."
        console.dir err
        else if r.statusCode != 200
        cb(null, st: r.statusCode, ms: r.statusMessage)
        else
        res = JSON.parse body
        cb(res)
        catch ex
        console.dir err: err, r: r, body: body
        console.dir ex

    }




    DOCOMO_API_KEY = process.env.DOCOMO_API_KEY
            DOCOMO_API = 'https://api.apigw.smt.docomo.ne.jp/dialogue/v1/dialogue'
    DOCOMO_AI_URL = "#{DOCOMO_API}?APIKEY=#{DOCOMO_API_KEY}"
    KEY_DOCOMO_CONTEXT = 'docomo-talk-context'
    KEY_DOCOMO_CONTEXT_TTL = 'docomo-talk-context-ttl'
    TTL_MINUTES = 20

}
