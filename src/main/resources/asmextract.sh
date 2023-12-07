savejson() {
		json=$(jq --arg name "$name" --arg key "$1" --argjson arr "$json_array" '
			if has($name) then
				.[$name] += {$key: $arr}
			else
				.[$name] = {$key: $arr}
			end
		' <<< "$json")
}
json="{}"
for file in ./data/pokemon/base_stats/*; do
	[ -f "$file" ] || continue
	name=$(basename "$file" .asm)
	name="${name//[^a-zA-Z0-9]/}"
	words=$(grep '^[[:space:]]*tmhm' "$file" | awk -F'tmhm ' '{print $2}')
	json_array='['
	first=true
	IFS=', ' read -ra ADDR <<< "$words"
	for i in "${ADDR[@]}"; do
		if [ "$first" = true ]; then
			first=false
		else
			json_array+=', '
		fi
		json_array+="\"$i\""
	done
	json_array+=']'
	savejson "tmhm"
	
	read -r -a stats < <(grep -h -E '.*db.*' $file | awk -F'[\t ,]+' 'NR==2{print $3,$4,$5,$6,$7,$8}')
	json_array=${stats[0]}
	savejson "hp"
	json_array=${stats[1]}
	savejson "atk"
	json_array=${stats[2]}
	savejson "def"
	json_array=${stats[3]}
	savejson "spd"
	json_array=${stats[4]}
	savejson "sat"
	json_array=${stats[5]}
	savejson "sdf"
	read -r -a types < <( grep -h -E '.*db.*' $file | awk -F'[\t ,]+' 'NR==3{print $3,$4}')
	json_array="[ \"${types[0]}\", \"${types[1]}\" ]"
	savejson "types"
done
json_array='['
name=""
while IFS= read -r line
do
		if [[ $line == *EvosAttacks: ]]
		then
			if [ ! -z "$name" ]
        then
            json_array=${json_array%,}
            json_array+=']'
						savejson "evos"
            json_array='['
        fi
        name=${line%EvosAttacks:}
				name=${name,,}
				name="${name//[^a-zA-Z0-9]/}"
    elif [ -z "$line" ]
    then
        json_array=${json_array%,}
        json_array+=']'
				savejson "evos"
        json_array='['
        name=""
    else
        word=$(echo $line | awk -F'[\t ,]+' '{print $3}')
        json_array+="\"$word\","
    fi
done < <(grep -h -E '.*EvosAttacks:|.*dbw.*,.*' ./data/pokemon/evos_attacks_*)
if [ ! -z "$name" ]
then
    json_array=${json_array%,}
    json_array+=']'
		savejson "evos"
fi
json_array='['
name=""
while IFS= read -r line
do
		if [[ $line == *EggMoves: ]]
		then
			if [ ! -z "$name" ]
        then
            json_array=${json_array%,}
            json_array+=']'
						savejson "eggs"
            json_array='['
        fi
        name=${line%EggMoves:}
				name=${name,,}
				name="${name//[^a-zA-Z0-9]/}"
    elif [ -z "$line" ]
    then
        json_array=${json_array%,}
        json_array+=']'
				savejson "eggs"
        json_array='['
        name=""
    else
        word=$(echo $line | awk -F'[\t ,]+' '{print $2}')
        json_array+="\"$word\","
    fi
done < <(grep -h -E '.*EggMoves:|\s+dw\s+[A-Za-z_]+$' ./data/pokemon/egg_moves_*)
if [ ! -z "$name" ]
then
    json_array=${json_array%,}
    json_array+=']'
		savejson "eggs"
fi
echo "$json"
