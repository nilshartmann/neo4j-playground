#! /bin/bash

base_dir=$PWD/$(dirname $0)
neo4j_dir=$base_dir/neo4j


${neo4j_dir}/bin/neo4j stop
