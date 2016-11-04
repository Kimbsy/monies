#!/bin/bash

# Get parameter values from the config file.
. "resources/config/config.properties"

# Delete the index.
curl -XDELETE 'http://'$ip_addr':9200/test?pretty'

# Create the index mappings.
curl -XPUT 'http://'$ip_addr':9200/test?pretty' -d '
{
  "mappings": {
    "thing": {
      "properties": {
        "entry_date": {
          "type": "date",
          "format": "epoch_millis"
        },
        "description": {
          "type": "string",
          "fields": {
            "description": {
              "type": "string"
            },
            "raw": {
              "type": "string",
              "index": "not_analyzed"
            }
          }
        },
        "entry_type": {
          "type": "string",
          "fields": {
            "description": {
              "type": "string"
            },
            "raw": {
              "type": "string",
              "index": "not_analyzed"
            }
          }
        },
        "change": {
          "type": "float"
        },
        "balance": {
          "type": "float"
        }
      }
    }
  }
}
'
