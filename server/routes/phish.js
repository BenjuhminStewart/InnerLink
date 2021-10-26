const API_KEY = process.env.PHISH_DOT_NET_KEY;

//express is the framework we're going to use to handle requests
const express = require("express");

//request module is needed to make a request to a web service
const request = require("request");

var router = express.Router();

/**
 * @api {get} /phish/blog/get Request a list of Phish.net Blogs
 * @apiName GetPhishBlogGet
 * @apiGroup Phish
 *
 * @apiHeader {String} authorization JWT provided from Auth get
 *
 * @apiDescription This end point is a pass through to the Phish.net API.
 * All parameters will pass on to https://api.phish.net/v3/blog/get.
 * See the <a href="https://phishnet.api-docs.io/v3/blog/blog-get">Phish.net documentation</a>
 * for a list of optional paramerters and expected results. You do not need a
 * Phish.net api key with this endpoint. Enjoy!
 */
router.get("/blog/get", (req, res) => {
  // for info on use of tilde (`) making a String literal, see below.
  //https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String
  let url = `https://api.phish.net/v3/blog/get?apikey=${API_KEY}`;

  //find the query string (parameters) sent to this end point and pass them on to
  // phish.net api call
  let n = req.originalUrl.indexOf("?") + 1;
  if (n > 0) {
    url += "&" + req.originalUrl.substring(n);
  }

  //When this web service gets a request, make a request to the Phish Web service
  request(url, function (error, response, body) {
    if (error) {
      res.send(error);
    } else {
      // pass on everything (try out each of these in Postman to see the difference)
      // res.send(response);

      // or just pass on the body

      var n = body.indexOf("{");
      var nakidBody = body.substring(n - 1);

      res.send(nakidBody);
    }
  });
});

module.exports = router;
