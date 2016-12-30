#! /bin/bash

base_dir=$PWD/$(dirname $0)
neo4j_dir=$base_dir/neo4j

echo Neo4jDir: ${neo4j_dir}

rm -rf ~/.neo4j/ ${neo4j_dir}/logs/  ${neo4j_dir}/certificates/ ${neo4j_dir}/data
