#! /bin/bash

base_dir=$PWD/$(dirname $0)
neo4j_dir=$base_dir/neo4j

echo Neo4jDir: ${neo4j_dir}

mkdir -p ${neo4j_dir}/logs/
mkdir -p ${neo4j_dir}/run/
mkdir -p ${neo4j_dir}/data/databases


${neo4j_dir}/bin/neo4j start
